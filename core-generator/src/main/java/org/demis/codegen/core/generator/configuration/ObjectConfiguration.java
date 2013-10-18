package org.demis.codegen.core.generator.configuration;

public class ObjectConfiguration {

    private String packageName;

    private String[] compositions;

    public ObjectConfiguration() {
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String[] getCompositions() {
        return compositions;
    }

    public void setCompositions(String[] compositions) {
        this.compositions = compositions;
    }
}
