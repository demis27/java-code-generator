package org.demis.codegen.core.generator.configuration;

import org.demis.codegen.core.generator.ConfigurationReader;
import org.demis.codegen.core.generator.ConfigurationWriter;
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
        CodeGeneratorConfiguration configuration = ConfigurationReader.readJSON("{\"databaseConfiguration\":{\"url\":" +
                "\"jdbc:postgresql://localhost:5432/test\",\"user\":\"test\",\"password\":\"test\",\"schema\":\"Test\"," +
                "\"readData\":false,\"filter\":null,\"h2scripts\":null,\"ddl\":null},\"objectConfiguration\":{" +
                "\"packageName\":\"org.demis.family\"},\"projectPath\":null,\"templatesPath\":null," +
                "\"filesConfiguration\":[{\"fileNameTemplate\":\"{packageName}/{className}.java\",\"templateName\":" +
                "\"templates/pojo/pojo-class.vm\",\"path\":\"src/main/generated-java\",\"target\":\"table\",\"name\":" +
                "\"pojo\",\"filters\":null,\"collision\":null}]}");
        Assert.assertNotNull(configuration);
        Assert.assertNotNull(configuration.getDatabaseConfiguration());
        Assert.assertEquals("jdbc:postgresql://localhost:5432/test", configuration.getDatabaseConfiguration().getUrl());
    }
}
