package org.demis.codegen.core.object;

public class Property {

    protected String name = null;

    protected String javaType = null;

    protected Entity owner = null;

    public Property() {
        // no op
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

}
