package org.demis.codegen.core.generator;

import org.apache.commons.io.IOUtils;
import org.demis.codegen.core.db.Schema;
import org.demis.codegen.core.generator.configuration.CodeGeneratorConfiguration;
import org.demis.codegen.core.generator.configuration.DatabaseConfiguration;
import org.demis.codegen.core.generator.configuration.ObjectConfiguration;
import org.demis.codegen.core.generator.configuration.TemplateConfiguration;
import org.demis.codegen.core.mapping.Mapping;
import org.demis.codegen.core.object.Entity;
import org.stringtemplate.v4.ST;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGeneratorTest {

    private CodeGeneratorConfiguration configuration;

    @BeforeMethod
    public void setUp() throws Exception {
        configuration = new CodeGeneratorConfiguration();
        configuration.setDefaultPackageName("org.demis");
        configuration.setProjectPath((new File("")).getAbsolutePath());
        configuration.setTemplatesPath(configuration.getProjectPath() + "/src/test/resources/");
        // Database
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        databaseConfiguration.setUrl("jdbc:postgresql://localhost:5432/test");
        databaseConfiguration.setSchema("Test");
        databaseConfiguration.setUser("test");
        databaseConfiguration.setPassword("test");
        configuration.setDatabaseConfiguration(databaseConfiguration);
        // object
        ObjectConfiguration objectConfiguration = new ObjectConfiguration();
        objectConfiguration.setPackageName("org.demis.family");
        configuration.setObjectConfiguration(objectConfiguration);
        // Template
        TemplateConfiguration templateConfiguration = new TemplateConfiguration();
        templateConfiguration.setFileNameTemplate("{packageName}/{className}Test.java");
        templateConfiguration.setPath("src/test/generated-java");
        templateConfiguration.setTarget("table");
        templateConfiguration.setTemplateName("/templates/test/pojo-test-class.st");
        configuration.addFileConfiguration(templateConfiguration);
    }

    @Test
    public void generate() throws Exception {
        CodeGenerator generator = new CodeGenerator(configuration);
        generator.generate();
        // schema
        Schema schema = generator.getSchema();
        Assert.assertNotNull(schema);
        Assert.assertNotNull(schema.getTables());
        Assert.assertEquals(schema.getTables().size(), 8);
        // objects
        List<Entity> entities = generator.getEntities();
        Assert.assertNotNull(entities);
        Assert.assertEquals(entities.size(), 8);
    }

    @Test
    public void parseFileName() {
        CodeGenerator generator = new CodeGenerator(configuration);
        Entity entity = new Entity();
        entity.setName("Family");

        Assert.assertEquals(generator.parseFileName(configuration, entity, "{packageName}/{className}Test.java"), "/org/demis/family/FamilyTest.java");
        Assert.assertEquals(generator.parseFileName(configuration, entity, "{defaultpackageName}/{className}Test.java"), "/org/demis/FamilyTest.java");

    }

    @Test
    public void getFileName() {
        CodeGenerator generator = new CodeGenerator(configuration);
        Entity entity = new Entity();
        entity.setName("Family");

        String filename = generator.getFileName(configuration, configuration.getFilesConfiguration().get(0), entity);
        Assert.assertEquals(filename, (new File("")).getAbsolutePath() + "/src/test/generated-java/org/demis/family/FamilyTest.java");
    }

    @Test
    public void generateFile() throws IOException {
        Map<String, Object> context = new HashMap<>();

        context.put("mapping", Mapping.getInstance());
        context.put("databaseConfiguration", configuration.getDatabaseConfiguration());
        context.put("objectConfiguration", configuration.getObjectConfiguration());
        context.put("defaultPackageName", configuration.getObjectConfiguration().getPackageName());
        context.put("configuration", configuration);
        context.put("templateConfiguration", configuration.getFilesConfiguration().get(0));

        InputStream stream = getClass().getResourceAsStream(configuration.getFilesConfiguration().get(0).getTemplateName());
        ST template = new ST(IOUtils.toString(IOUtils.toByteArray(stream), "UTF-8"));

        Entity entity = new Entity();
        entity.setName("Family");
        context.put("entity", entity);

        CodeGenerator generator = new CodeGenerator(configuration);
        generator.generateFile(template, context);
        String filename = generator.getFileName(configuration, configuration.getFilesConfiguration().get(0), entity);
        Assert.assertTrue((new File(filename)).exists());
    }

    @Test
    public void convertPackageNameToPath() {
        CodeGenerator generator = new CodeGenerator(configuration);

        Assert.assertEquals(generator.convertPackageNameToPath("org.demis.family"), "/org/demis/family");
        Assert.assertEquals(generator.convertPackageNameToPath("org.demis"), "/org/demis");
        Assert.assertEquals(generator.convertPackageNameToPath("org"), "/org");


    }
}
