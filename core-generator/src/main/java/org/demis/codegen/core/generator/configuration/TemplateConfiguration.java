package org.demis.codegen.core.generator.configuration;

public class TemplateConfiguration {

    private String fileNameTemplate;

    private String templateName;

    private String path;

    private String target;

    private String name;

    private CollisionTemplateConfiguration collision;

    private String[] usedFilters;

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

    public CollisionTemplateConfiguration getCollision() {
        return collision;
    }

    public void setCollision(CollisionTemplateConfiguration collision) {
        this.collision = collision;
    }

    public String[] getUsedFilters() {
        return usedFilters;
    }

    public void setUsedFilters(String[] usedFilters) {
        this.usedFilters = usedFilters;
    }
}
