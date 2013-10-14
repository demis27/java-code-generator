package org.demis.codegen.core.generator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.demis.codegen.core.db.Table;
import org.demis.codegen.core.db.reader.DatabaseReadingException;
import org.demis.codegen.core.db.reader.PostgresSchemaReader;
import org.demis.codegen.core.generator.configuration.*;
import org.demis.codegen.core.generator.configuration.CodeGeneratorConfiguration;

import java.io.*;
import java.util.*;

import org.demis.codegen.core.db.Schema;
import org.demis.codegen.core.generator.configuration.filter.DatabaseFilter;
import org.demis.codegen.core.mapping.DataBaseToObjectConverter;
import org.demis.codegen.core.mapping.Mapping;
import org.demis.codegen.core.object.Entity;
import org.demis.codegen.core.object.EntityPackage;
import org.demis.codegen.core.object.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;

public class CodeGenerator {

    private final Logger logger = LoggerFactory.getLogger(CodeGenerator.class);

    private CodeGeneratorConfiguration configuration;

//    private NameHelper nameHelper = new NameHelper();

    public CodeGenerator(CodeGeneratorConfiguration configuration) {
        this.configuration = configuration;
    }
    private Schema schema;
    private EntityPackage entityPackage;

    public void generate() throws DatabaseReadingException, IOException {
        schema = readSchema(configuration.getDatabaseConfiguration());

        entityPackage = generateObjectModel(configuration, schema);

        // filter
        List<Entity> entities = new ArrayList<>();
        for (Entity entity : entityPackage.getEntities()) {
            Table table = Mapping.getInstance().getTable(entity);
            boolean filtered = false;
            if (configuration.getDatabaseConfiguration().getFilters() != null && configuration.getDatabaseConfiguration().getFilters().size() != 0) {
                for (DatabaseFilter filter: configuration.getDatabaseConfiguration().getFilters()) {
                    filtered = filtered || (filter.match(table.getName()) && (filter.getTarget() == DatabaseFilter.DatabaseFilterTarget.TABLE));
                }
            }
            if (!filtered) {
                entities.add(entity);
            }
        }
        entityPackage.setEntities(entities);

        Map<String, Object> context = new HashMap<>();
        context.put("mapping", Mapping.getInstance());
        context.put("package", entityPackage);
        context.put("databaseConfiguration", configuration.getDatabaseConfiguration());
        context.put("objectConfiguration", configuration.getObjectConfiguration());
        context.put("defaultPackageName", configuration.getObjectConfiguration().getPackageName());
        context.put("configuration", configuration);

        for (TemplateConfiguration templateConfiguration : configuration.getFilesConfiguration()) {

            context.put("templateConfiguration", templateConfiguration);
            if (templateConfiguration.getTarget().equals("column")) {
                for (Entity entity : entityPackage.getEntities()) {
                    for (Property property: entity.getProperties()) {
                        context.put("propertyDescriptor", property);
                        InputStream stream = new FileInputStream(configuration.getTemplatesPath() + templateConfiguration.getTemplateName());
                        ST template = new ST(IOUtils.toString(IOUtils.toByteArray(stream), "UTF-8"), '$', '$');
                        generateFile(template, context);
                    }
                }
            } else if (templateConfiguration.getTarget().equals("table")) {
                for (Entity entity : entityPackage.getEntities()) {
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

    public String getCollisionFileName(CodeGeneratorConfiguration configuration, TemplateConfiguration templateConfiguration, Entity entity) {
        String projectPath = configuration.getProjectPath();
        String path = templateConfiguration.getPath();

        String filename = projectPath + "/" + path + templateConfiguration.getCollision().getResultPath();
        filename = parseFileName(configuration, entity, filename);

        return filename;
    }

    public void generateFile(ST template, Map<String, Object> context) {
        // Generate the result
        for (String entry: context.keySet()) {
            template.add(entry, context.get(entry));
        }
        String filename;
        // Test for collision
        if (((TemplateConfiguration)context.get("templateConfiguration")).getCollision() != null &&
                ((TemplateConfiguration)context.get("templateConfiguration")).getCollision().isManage()) {
            String checkFilename = getFileName((CodeGeneratorConfiguration)context.get("configuration"), (TemplateConfiguration)context.get("templateConfiguration"), (Entity)context.get("entity"));
            File file = new File(checkFilename);
            if (file.exists()) {
                filename = getCollisionFileName((CodeGeneratorConfiguration)context.get("configuration"), (TemplateConfiguration)context.get("templateConfiguration"), (Entity)context.get("entity"));
                context.put("collision", Boolean.FALSE);
            }
            else {
                filename = getFileName((CodeGeneratorConfiguration)context.get("configuration"), (TemplateConfiguration)context.get("templateConfiguration"), (Entity)context.get("entity"));
            }
        }
        else {
            filename = getFileName((CodeGeneratorConfiguration)context.get("configuration"), (TemplateConfiguration)context.get("templateConfiguration"), (Entity)context.get("entity"));
        }

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

    public EntityPackage generateObjectModel(CodeGeneratorConfiguration configuration, Schema schema) {
        EntityPackage entityPackage = DataBaseToObjectConverter.getInstance().convertSchema(schema);
        entityPackage.setName(configuration.getObjectConfiguration().getPackageName());

        for (Entity entity : entityPackage.getEntities()) {
            Table table = Mapping.getInstance().getTable(entity);
            Mapping.getInstance().addMapping(table, entity);
        }
        return entityPackage;
    }

    public String parseFileName(CodeGeneratorConfiguration configuration, Entity entity, String templateFileName) {
        String result = templateFileName;
        if (entity != null) {
            result = result.replaceAll("\\{className\\}", entity.getName());
//            result = result.replaceAll("\\{objectName\\}", entity.getObjectName());
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

    public EntityPackage getEntityPackage() {
        return entityPackage;
    }

    public Schema getSchema() {
        return schema;
    }
}
