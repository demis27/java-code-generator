package org.demis.codegen.core.generator.configuration;

import org.demis.codegen.core.generator.ConfigurationReader;
import org.demis.codegen.core.generator.ConfigurationWriter;
import org.demis.codegen.core.generator.configuration.filter.DatabaseFilter;
import org.demis.codegen.core.generator.configuration.filter.Filter;
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
        String[] compositions = {"compo1", "compo2"};
        objectConfiguration.setCompositions(compositions);
        configuration.setObjectConfiguration(objectConfiguration);
        // database configuration
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        databaseConfiguration.setUrl("jdbc:postgresql://localhost:5432/test");
        databaseConfiguration.setSchema("Test");
        databaseConfiguration.setUser("test");
        databaseConfiguration.setPassword("test");
        configuration.setDatabaseConfiguration(databaseConfiguration);
        // Databse configuration filter
        DatabaseFilter filter = new DatabaseFilter("hibernate tmp", "ht_+", DatabaseFilter.DatabaseFilterTarget.TABLE);
        filter.setScope(Filter.FilterScope.GLOBAL);
        filter.setType(Filter.FilterType.INCLUDE);
        databaseConfiguration.addFilter(filter);

        // files
        TemplateConfiguration pojo = new TemplateConfiguration();
        pojo.setName("pojo");
        pojo.setFileNameTemplate("{packageName}/{className}.java");
        pojo.setTemplateName("templates/pojo/pojo-class.vm");
        pojo.setPath("src/main/generated-java");
        pojo.setTarget("table");
        String[] filters = {"hibernate tmp"};
        pojo.setUsedFilters(filters);
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
        CodeGeneratorConfiguration configuration = ConfigurationReader.readJSON("{ \"databaseConfiguration\" : { \"ddl\" : null,\n" +
                "      \"filters\" : [ { \"expression\" : \"ht_+\",\n" +
                "            \"name\" : \"hibernate tmp\",\n" +
                "            \"scope\" : \"GLOBAL\",\n" +
                "            \"target\" : \"TABLE\",\n" +
                "            \"type\" : \"INCLUDE\"\n" +
                "          } ],\n" +
                "      \"h2scripts\" : null,\n" +
                "      \"password\" : \"test\",\n" +
                "      \"readData\" : false,\n" +
                "      \"schema\" : \"Test\",\n" +
                "      \"url\" : \"jdbc:postgresql://localhost:5432/test\",\n" +
                "      \"user\" : \"test\"\n" +
                "    },\n" +
                "  \"defaultPackageName\" : null,\n" +
                "  \"filesConfiguration\" : [ { \"collision\" : { \"checkPath\" : \"src/main/java\",\n" +
                "            \"filename\" : \"{packageName}/{className}Base.java\",\n" +
                "            \"manage\" : true,\n" +
                "            \"resultPath\" : \"src/main/generated-java\"\n" +
                "          },\n" +
                "        \"fileNameTemplate\" : \"{packageName}/{className}.java\",\n" +
                "        \"name\" : \"pojo\",\n" +
                "        \"path\" : \"src/main/generated-java\",\n" +
                "        \"target\" : \"table\",\n" +
                "        \"templateName\" : \"templates/pojo/pojo-class.vm\",\n" +
                "        \"usedFilters\" : [ \"hibernate tmp\" ]\n" +
                "      } ],\n" +
                "  \"objectConfiguration\" : { \"compositions\" : [ \"compo1\",\n" +
                "          \"compo2\"\n" +
                "        ],\n" +
                "      \"packageName\" : \"org.demis.family\"\n" +
                "    },\n" +
                "  \"projectPath\" : \".\",\n" +
                "  \"templatesPath\" : null\n" +
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
        Assert.assertEquals(filter.getName(), "hibernate tmp");
        Assert.assertEquals(filter.getScope(), Filter.FilterScope.GLOBAL);
        Assert.assertEquals(filter.getType(), Filter.FilterType.INCLUDE);

        Assert.assertNotNull(configuration.getFilesConfiguration());
        Assert.assertNotNull(configuration.getFilesConfiguration().get(0));
        Assert.assertNotNull(configuration.getFilesConfiguration().get(0).getUsedFilters());
        Assert.assertEquals(configuration.getFilesConfiguration().get(0).getUsedFilters().length, 1);
        Assert.assertNotNull(configuration.getFilesConfiguration().get(0).getCollision());

        Assert.assertNotNull(configuration.getObjectConfiguration());
        Assert.assertEquals(configuration.getObjectConfiguration().getPackageName(), "org.demis.family");
        Assert.assertEquals(configuration.getObjectConfiguration().getCompositions().length, 2);



    }
}
