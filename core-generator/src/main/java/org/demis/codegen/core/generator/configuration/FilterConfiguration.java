package org.demis.codegen.core.generator.configuration;

public class FilterConfiguration {

    private String classname;

    public FilterConfiguration() {
    }

    public FilterConfiguration(String classname) {
        this.classname = classname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
