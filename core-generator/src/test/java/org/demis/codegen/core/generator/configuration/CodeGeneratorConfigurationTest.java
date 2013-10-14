package org.demis.codegen.core.generator.configuration;

import org.demis.codegen.core.generator.ConfigurationReader;
import org.demis.codegen.core.generator.ConfigurationWriter;
import org.demis.codegen.core.generator.configuration.filter.DatabaseFilter;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CodeGeneratorConfigurationTest {

    @Test
    public void writeCodeGeneratorConfiguration() throws IOException {
        CodeGeneratorConfiguration configuration = new CodeGeneratorConfiguration();
        // object configuration
        ObjectConfiguration objectConfiguration = new ObjectConfiguration();
        objectConfiguration.setPackageName("org.demis.family");
        configuration.setObjectConfiguration(objectConfiguration);
        // database configuration
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        databaseConfiguration.setUrl("jdbc:postgresql://localhost:5432/test");
        databaseConfiguration.setSchema("Test");
        databaseConfiguration.setUser("test");
        databaseConfiguration.setPassword("test");
        configuration.setDatabaseConfiguration(databaseConfiguration);
        // Databse configuration filter
        DatabaseFilter filter = new DatabaseFilter("ht_+", DatabaseFilter.DatabaseFilterTarget.TABLE);
        databaseConfiguration.addFilter(filter);

        // files
        TemplateConfiguration pojo = new TemplateConfiguration();
        pojo.setName("pojo");
        pojo.setFileNameTemplate("{packageName}/{className}.java");
        pojo.setTemplateName("templates/pojo/pojo-class.vm");
        pojo.setPath("src/main/generated-java");
        pojo.setTarget("table");
        // Collision
        CollisionTemplateConfiguration collision = new CollisionTemplateConfiguration();
        collision.setManage(true);
        collision.setFilename("{packageName}/{className}Base.java");
        collision.setResultPath("src/main/generated-java");
        collision.setCheckPath("src/main/java");
        pojo.setCollision(collision);
        configuration.addFileConfiguration(pojo);

        String json = ConfigurationWriter.toJSON(configuration);

        System.out.println(json);
    }

    @Test
    public void readCodeGeneratorConfiguration() throws IOException {
        CodeGeneratorConfiguration configuration = ConfigurationReader.readJSON("{\n" +
                "    \"databaseConfiguration\": {\n" +
                "        \"url\": \"jdbc:postgresql://localhost:5432/test\",\n" +
                "        \"user\": \"test\",\n" +
                "        \"password\": \"test\",\n" +
                "        \"schema\": \"Test\",\n" +
                "        \"readData\": false,\n" +
                "        \"h2scripts\": null,\n" +
                "        \"ddl\": null,\n" +
                "        \"filters\": [\n" +
                "            {\n" +
                "                \"expression\": \"ht_+\",\n" +
                "                \"target\": \"TABLE\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"objectConfiguration\": {\n" +
                "        \"packageName\": \"org.demis.family\"\n" +
                "    },\n" +
                "    \"defaultPackageName\": null,\n" +
                "    \"projectPath\": \".\",\n" +
                "    \"templatesPath\": null,\n" +
                "    \"filesConfiguration\": [\n" +
                "        {\n" +
                "            \"fileNameTemplate\": \"{packageName}/{className}.java\",\n" +
                "            \"templateName\": \"templates/pojo/pojo-class.vm\",\n" +
                "            \"path\": \"src/main/generated-java\",\n" +
                "            \"target\": \"table\",\n" +
                "            \"name\": \"pojo\",\n" +
                "            \"collision\": {\n" +
                "                \"manage\": true,\n" +
                "                \"checkPath\": \"src/main/java\",\n" +
                "                \"resultPath\": \"src/main/generated-java\",\n" +
                "                \"filename\": \"{packageName}/{className}Base.java\"\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}");
        Assert.assertNotNull(configuration);
        Assert.assertNotNull(configuration.getDatabaseConfiguration());
        Assert.assertEquals("jdbc:postgresql://localhost:5432/test", configuration.getDatabaseConfiguration().getUrl());
        DatabaseConfiguration databaseConfiguration = configuration.getDatabaseConfiguration();
        Assert.assertNotNull(databaseConfiguration);
        DatabaseFilter filter = databaseConfiguration.getFilters().get(0);
        Assert.assertNotNull(filter);
        Assert.assertEquals(filter.getTarget(), DatabaseFilter.DatabaseFilterTarget.TABLE);
        Assert.assertEquals(filter.getExpression(), "ht_+");

        Assert.assertNotNull(configuration.getFilesConfiguration());
        Assert.assertNotNull(configuration.getFilesConfiguration().get(0));
        Assert.assertNotNull(configuration.getFilesConfiguration().get(0).getCollision());
    }
}
