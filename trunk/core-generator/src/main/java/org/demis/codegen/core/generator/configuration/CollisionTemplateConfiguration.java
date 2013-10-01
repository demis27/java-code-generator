package org.demis.codegen.core.generator.configuration;

public class CollisionTemplateConfiguration {

    private boolean manage = false;

    private String checkPath;

    private String resultPath;

    private String filename;

    public CollisionTemplateConfiguration() {
    }

    public String getCheckPath() {
        return checkPath;
    }

    public void setCheckPath(String checkPath) {
        this.checkPath = checkPath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean isManage() {
        return manage;
    }

    public void setManage(boolean manage) {
        this.manage = manage;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }
}
