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
        configuration.addFileConfiguration(pojo);

        String json = ConfigurationWriter.toJSON(configuration);

        System.out.println(json);
    }

    @Test
    public void readCodeGeneratorConfiguration() throws IOException {
        CodeGeneratorConfiguration configuration = ConfigurationReader.readJSON("{\"databaseConfiguration\":{\"url\":\"jdbc:postgresql://localhost:5432/test\",\"user\":\"test\",\"password\":\"test\",\"schema\":\"Test\",\"readData\":false,\"h2scripts\":null,\"ddl\":null,\"filters\":[{\"expression\":\"ht_+\",\"target\":\"TABLE\"}]},\"objectConfiguration\":{\"packageName\":\"org.demis.family\"},\"defaultPackageName\":null,\"projectPath\":\".\",\"templatesPath\":null,\"filesConfiguration\":[{\"fileNameTemplate\":\"{packageName}/{className}.java\",\"templateName\":\"templates/pojo/pojo-class.vm\",\"path\":\"src/main/generated-java\",\"target\":\"table\",\"name\":\"pojo\",\"collision\":null}]}");
        Assert.assertNotNull(configuration);
        Assert.assertNotNull(configuration.getDatabaseConfiguration());
        Assert.assertEquals("jdbc:postgresql://localhost:5432/test", configuration.getDatabaseConfiguration().getUrl());
        DatabaseConfiguration databaseConfiguration = configuration.getDatabaseConfiguration();
        Assert.assertNotNull(databaseConfiguration);
        DatabaseFilter filter = databaseConfiguration.getFilters().get(0);
        Assert.assertNotNull(filter);
        Assert.assertEquals(filter.getTarget(), DatabaseFilter.DatabaseFilterTarget.TABLE);
        Assert.assertEquals(filter.getExpression(), "ht_+");
    }
}
