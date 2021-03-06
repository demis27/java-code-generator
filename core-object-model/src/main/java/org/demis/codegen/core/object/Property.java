package org.demis.codegen.core.object;

import org.demis.codegen.core.db.Column;
import org.demis.codegen.util.NameUtil;

public class Property {

    protected String name = null;

    protected String javaType = null;

    protected Entity owner = null;

    protected Column column = null;

    protected boolean isId = false;

    protected boolean isManyToOne = false;

    public Property() {
        // no op
    }

    public String getName() {
        return name;
    }

    public String getPlural() {
        return NameUtil.getPlural(name);
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

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public boolean isId() {
        return isId;
    }

    public void setId(boolean id) {
        isId = id;
    }

    public String getNameAsSuffix() {
        return NameUtil.toUpperCaseFirst(name);
    }

    public boolean isManyToOne() {
        return isManyToOne;
    }

    public void setManyToOne(boolean manyToOne) {
        isManyToOne = manyToOne;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", javaType='" + javaType + '\'' +
                ", owner=" + (owner != null ? owner.getName() : "null") +
                ", id=" + isId +
                '}';
    }
}
