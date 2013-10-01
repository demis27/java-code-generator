package org.demis.codegen.core.generator.configuration;

import java.util.ArrayList;
import java.util.List;

public class CodeGeneratorConfiguration {

    private DatabaseConfiguration databaseConfiguration;

    private ObjectConfiguration objectConfiguration;

    private List<TemplateConfiguration> files = new ArrayList<>();

    private String defaultPackageName;

    private String projectPath = ".";

    private String templatesPath ;

    public CodeGeneratorConfiguration() {
    }

    public DatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    public void setDatabaseConfiguration(DatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    public ObjectConfiguration getObjectConfiguration() {
        return objectConfiguration;
    }

    public void setObjectConfiguration(ObjectConfiguration objectConfiguration) {
        this.objectConfiguration = objectConfiguration;
    }

    public void addFileConfiguration(TemplateConfiguration fileConfiguration) {
        files.add(fileConfiguration);
    }

    public List<TemplateConfiguration> getFilesConfiguration() {
        return files;
    }

    public String getTemplatesPath() {
        return templatesPath;
    }

    public void setTemplatesPath(String templatesPath) {
        this.templatesPath = templatesPath;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getDefaultPackageName() {
        return defaultPackageName;
    }

    public void setDefaultPackageName(String defaultPackageName) {
        this.defaultPackageName = defaultPackageName;
    }
}
