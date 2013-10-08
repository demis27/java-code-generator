package org.demis.codegen.core.generator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.demis.codegen.core.db.Table;
import org.demis.codegen.core.db.reader.DatabaseReadingException;
import org.demis.codegen.core.db.reader.PostgresSchemaReader;
import org.demis.codegen.core.generator.configuration.*;
import org.demis.codegen.core.generator.configuration.CodeGeneratorConfiguration;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.demis.codegen.core.db.Schema;
import org.demis.codegen.core.generator.configuration.filter.DatabaseFilter;
import org.demis.codegen.core.mapping.DataBaseToObjectConverter;
import org.demis.codegen.core.mapping.Mapping;
import org.demis.codegen.core.object.Entity;
import org.demis.codegen.core.object.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;

/**
 * @version 1.0
 * @author <a href="mailto:demis27@demis27.net">Stéphane kermabon</a>
 */
public class CodeGenerator {

    private final Logger logger = LoggerFactory.getLogger(CodeGenerator.class);

    private CodeGeneratorConfiguration configuration;

//    private NameHelper nameHelper = new NameHelper();

    public CodeGenerator(CodeGeneratorConfiguration configuration) {
        this.configuration = configuration;
    }
    private Schema schema;
    private List<Entity> entities;

    public void generate() throws DatabaseReadingException, IOException {
        schema = readSchema(configuration.getDatabaseConfiguration());

        entities = generateObjectModel(configuration, schema);

        Map<String, Object> context = new HashMap<>();

        context.put("mapping", Mapping.getInstance());
        context.put("databaseConfiguration", configuration.getDatabaseConfiguration());
        context.put("objectConfiguration", configuration.getObjectConfiguration());
        context.put("defaultPackageName", configuration.getObjectConfiguration().getPackageName());
        context.put("configuration", configuration);

        for (TemplateConfiguration templateConfiguration : configuration.getFilesConfiguration()) {

            context.put("templateConfiguration", templateConfiguration);
            if (templateConfiguration.getTarget().equals("column")) {
                for (Entity entity : entities) {
                    for (Property property: entity.getProperties()) {
                        context.put("propertyDescriptor", property);
                        InputStream stream = new FileInputStream(configuration.getTemplatesPath() + templateConfiguration.getTemplateName());
                        ST template = new ST(IOUtils.toString(IOUtils.toByteArray(stream), "UTF-8"), '$', '$');
                        generateFile(template, context);
                    }
                }
            } else if (templateConfiguration.getTarget().equals("table")) {
                for (Entity entity : entities) {
                    Table table = Mapping.getInstance().getTable(entity);
                    boolean filtered = false;
                    if (configuration.getDatabaseConfiguration().getFilters() != null && configuration.getDatabaseConfiguration().getFilters().size() != 0) {
                        for (DatabaseFilter filter: configuration.getDatabaseConfiguration().getFilters()) {
                            filtered = filtered || (filter.match(table.getName()) && (filter.getTarget() == DatabaseFilter.DatabaseFilterTarget.TABLE));
                        }
                    }
                    if (!filtered) {
                        logger.debug("Generation file for template : " + templateConfiguration.getName() + " for table " + table.getName());
                        context.put("entity", entity);
                        InputStream stream = new FileInputStream(configuration.getTemplatesPath() + templateConfiguration.getTemplateName());
                        ST template = new ST(IOUtils.toString(IOUtils.toByteArray(stream), "UTF-8"), '$', '$');
                        generateFile(template, context);
                    }
                    else {
                        logger.debug("No generation file for template : " + templateConfiguration.getName() + " for table " + table.getName());
                    }
                }
            } else if (templateConfiguration.getTarget().equals("schema")) {
                context.put("schema", schema);
                InputStream stream = new FileInputStream(configuration.getTemplatesPath() + templateConfiguration.getTemplateName());
                ST template = new ST(IOUtils.toString(IOUtils.toByteArray(stream), "UTF-8"), '$', '$');
                generateFile(template, context);
            }
        }
    }

    public String getFileName(CodeGeneratorConfiguration configuration, TemplateConfiguration templateConfiguration, Entity entity) {
        String projectPath = configuration.getProjectPath();
        String path = templateConfiguration.getPath();

        String filename = projectPath + "/" + path + templateConfiguration.getFileNameTemplate();
        filename = parseFileName(configuration, entity, filename);

        return filename;
    }

    public void generateFile(ST template, Map<String, Object> context) {
        // Generate the result
        for (String entry: context.keySet()) {
            template.add(entry, context.get(entry));
        }
        // Save the result
        String filename = getFileName((CodeGeneratorConfiguration)context.get("configuration"), (TemplateConfiguration)context.get("templateConfiguration"), (Entity)context.get("entity"));
        FileOutputStream fileOutput = null;

        File filePath = new File(filename.substring(0, filename.lastIndexOf('/')));
        try {
            FileUtils.forceMkdir(filePath);
            fileOutput = new FileOutputStream(filename);
            logger.info("Generate file = " + filename);
            fileOutput.write(template.render().getBytes());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally {
            try {
                fileOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


/*
        String path = null;
        String projectPath = null;
        String checkFilename, collisionFilename;

        try {
            TemplateConfiguration templateConfiguration = ((TemplateConfiguration) (context.get("templateConfiguration")));
            Entity entities = (ClassDescriptor) context.get("entities");
            CollisionTemplateConfiguration collision = ((TemplateConfiguration) (context.get("templateConfiguration"))).getCollision();

            // project path
            projectPath = ((CodeGeneratorConfiguration) (context.get("configuration"))).getProjectPath();

            // generationFilename
            path = templateConfiguration.getPath();
            filename = projectPath + File.separator + path + File.separator + templateConfiguration.getFileNameTemplate();
            filename = parseFileName(entities, filename);

            context.put("collision", Boolean.FALSE);
            // check collision
            if (collision != null && collision.isManage()) {

                // checkFilename
                checkFilename = projectPath + File.separator + collision.getCheckPath() + File.separator + templateConfiguration.getFileNameTemplate();
                if (entities != null) {
                    checkFilename = parseFileName(entities, checkFilename);
                }
                File checkFile = new File(checkFilename);
                if (checkFile.exists()) {
                    collisionFilename = projectPath + File.separator + collision.getResultPath() + File.separator + collision.getFilename();
                    filename = parseFileName(entities, collisionFilename);
                    context.put("collision", Boolean.TRUE);
                    logger.info("generate file with filename for collision : " + filename);
                }
            }


//            logger.info("collision for template " + templateConfiguration.getName()
//                    + " and " + entities.getClassName() + " : "
//                    + (collision != null ? collision.isManage() : "null")
//                    + " file exist : " + filename + " " + generationFile.exists());


            // call template
            StringWriter writer = new StringWriter();
            velocityTemplate.merge(context, writer);
            writer.toString().getBytes());

            if (logger.isInfoEnabled()) {
                logger.info("generate file with filename : " + filename);
                logger.info("generate file with path : " + path);
            }

        } catch (ResourceNotFoundException ex) {
            logger.error("Error when generate file : filename = " + filename, ex);
        } catch (ParseErrorException ex) {
            logger.error("Error when generate file : filename = " + filename, ex);
        } catch (MethodInvocationException ex) {
            logger.error("Error when generate file : filename = " + filename, ex);
        } catch (IOException ex) {
            logger.error("Error when generate file : filename = " + filename, ex);
        }
        finally {
            if (fileOutput != null) {
                try {
                    fileOutput.close();
                } catch (IOException ex) {
                    logger.error("Error when generate file : filename = " + filename, ex);
                }
            }
        }
*/
    }

    public Schema readSchema(DatabaseConfiguration databaseConfiguration) throws DatabaseReadingException {

        org.demis.codegen.core.db.reader.DatabaseConfiguration coredbConfiguration = null;
//        if (databaseConfiguration.getUrl() != null) {
            coredbConfiguration = new org.demis.codegen.core.db.reader.DatabaseConfiguration();
            coredbConfiguration.setUrl(databaseConfiguration.getUrl());
//            coredbConfiguration.setDbms(SupportedDBMS.POSTGRESQL8);
            coredbConfiguration.setPassword(databaseConfiguration.getPassword());
            coredbConfiguration.setSchemaName(databaseConfiguration.getSchema());
            coredbConfiguration.setUser(databaseConfiguration.getUser());
//            coredbConfiguration.setFilter(databaseConfiguration.getFilter());
//            coredbConfiguration.setReadData(databaseConfiguration.isReadData());
//        } else if (databaseConfiguration.getH2scripts() != null) {
//            coredbConfiguration = H2Utils.createDatabase(projectPath, databaseConfiguration.getH2scripts()) ;
//        }
//        else if (databaseConfiguration.getDdl() != null) {
//            coredbConfiguration = DdlUtils.createDatabase(databaseConfiguration.getDdl()) ;
//        }

        PostgresSchemaReader postgresSchemaReader = new PostgresSchemaReader();
        return postgresSchemaReader.read(coredbConfiguration);

/*
        if (coredbConfiguration.getDbms() == SupportedDBMS.POSTGRESQL8) {
            logger.info("readJSONFile postgresql schema");
            postgresSchemaReader = new PostgresqlSchemaReader();
            return postgresSchemaReader.readJSONFile(coredbConfiguration);
        } else if (coredbConfiguration.getDbms() == SupportedDBMS.H2) {
            logger.info("readJSONFile generic schema");
            postgresSchemaReader = new H2SchemaReader();
            return postgresSchemaReader.readJSONFile(coredbConfiguration);
        } else if (coredbConfiguration.getDbms() == SupportedDBMS.GENERIC) {
            logger.info("readJSONFile generic schema");
            postgresSchemaReader = new GenericSchemaReader();
            return postgresSchemaReader.readJSONFile(coredbConfiguration);
        }
        else {
            logger.info("Not supported DBMS : " + databaseConfiguration.getType());
        }
*/
    }

    public List<Entity> generateObjectModel(CodeGeneratorConfiguration configuration, Schema schema) {
        List<Entity> entities = DataBaseToObjectConverter.getInstance().convertSchema(schema);


        for (Entity entity : entities) {
            Table table = Mapping.getInstance().getTable(entity);


//            PackageDescriptor packageDescriptor = new PackageDescriptor();
//            packageDescriptor.setName(configuration.getObjectConfiguration().getPackageName() + "." + nameHelper.getPackageName(entity));

//            entity.setPackageDescriptor(packageDescriptor);
            Mapping.getInstance().addMapping(table, entity);
        }

        return entities;
    }

    public String parseFileName(CodeGeneratorConfiguration configuration, Entity entity, String templateFileName) {
        String result = templateFileName;
        if (entity != null) {
            result = result.replaceAll("\\{className\\}", entity.getName());
//            result = result.replaceAll("\\{objectName\\}", entity.getObjectName());
//            PackageDescriptor packageDescriptor = entity.getPackageDescriptor();
        }
        if (configuration.getObjectConfiguration().getPackageName() != null) {
            result = result.replaceAll("\\{packageName\\}", convertPackageNameToPath(configuration.getObjectConfiguration().getPackageName()));
        }
        if (configuration.getDefaultPackageName() != null) {
            result = result.replaceAll("\\{defaultpackageName\\}", convertPackageNameToPath(configuration.getDefaultPackageName()));
        }
        logger.debug("parseFileName( " + entity + "," + templateFileName + " ) to " + result );
        return result;
    }

    public String convertPackageNameToPath(String pakageName) {
        StringTokenizer tokenizer = new StringTokenizer(pakageName, ".");
        StringBuffer buffer = new StringBuffer("");
        while (tokenizer.hasMoreTokens()) {
            buffer.append("/");
            buffer.append(tokenizer.nextToken());
        }
        return buffer.toString();
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Schema getSchema() {
        return schema;
    }
}
