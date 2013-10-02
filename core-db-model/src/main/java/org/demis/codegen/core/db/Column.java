package org.demis.codegen.core.db;

import org.apache.commons.lang.StringUtils;

public class Column {

    private String name = null;

    private boolean primaryKey = false;

    private boolean notNull = false;

    private int size = 0;

    private int scale = 0;

    private int type = 0;

    private boolean foreignKey = false;

    private Table table = null;

    private boolean noref = true;

    private String remarks = null;

    private String defaultValue = null;

    private boolean unique = false;

    private int position;

    public Column() {
        // no op
    }

    public Column(Table table, String name) {
        this.table = table;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public boolean isNullable() {
        return !notNull;
    }

    public void setNullable(boolean nullable) {
        this.notNull = !nullable;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int columnSize) {
        this.size = columnSize;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(boolean isForeignKey) {
        this.foreignKey = isForeignKey;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public boolean haveNoref() {
        return noref;
    }

    public void setNoref(boolean noref) {
        this.noref = noref;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean haveRemarks() {
        return !StringUtils.isEmpty(remarks);
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        if (name != null ? !name.equals(column.name) : column.name != null) return false;
        if (table != null ? !table.equals(column.table) : column.table != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (table != null ? table.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", primaryKey=" + primaryKey +
                ", notNull=" + notNull +
                ", size=" + size +
                ", scale=" + scale +
                ", type=" + type +
                ", foreignKey=" + foreignKey +
                ", table=" + (table != null ? table.getName() : "null") +
                ", noref=" + noref +
                ", remarks='" + remarks + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", unique=" + unique +
                ", position=" + position +
                '}';
    }
}
