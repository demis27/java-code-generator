package org.demis.codegen.core.generator.configuration;

import java.util.List;

public class TemplateConfiguration {

    private String fileNameTemplate;

    private String templateName;

    private String path;

    private String target;

    private String name;

    private List<FilterConfiguration> filters;

    private CollisionTemplateConfiguration collision;

    public TemplateConfiguration() {
    }

    public String getFileNameTemplate() {
        return fileNameTemplate;
    }

    public void setFileNameTemplate(String fileNameTemplate) {
        this.fileNameTemplate = fileNameTemplate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public List<FilterConfiguration> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterConfiguration> filters) {
        this.filters = filters;
    }

    public CollisionTemplateConfiguration getCollision() {
        return collision;
    }

    public void setCollision(CollisionTemplateConfiguration collision) {
        this.collision = collision;
    }
}
