package org.demis.codegen.core.db;

public class PrimaryKey extends ColumnReferencesHolder {

    private String name = null;

    private Table table = null;

    private String remarks = null;

    public PrimaryKey() {
        // no op
    }

    public PrimaryKey(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        if (this.table != table) {
            this.table = table;
            table.setPrimaryKey(this);
        }
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrimaryKey that = (PrimaryKey) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (table != null ? !table.equals(that.table) : that.table != null) return false;

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
        return "PrimaryKey{" +
                "name='" + name + '\'' +
                ", table=" + (table != null ? table.getName() : "null") +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
