package org.demis.codegen.core.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schema {

    private String name = null;

    private Map<String, Table> tables = new HashMap<>();

    public Schema() {
        // no op
    }

    public Schema(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTable(Table table) {
        tables.put(table.getName(), table);
        table.setSchema(this);
    }

    public void addTables(List<Table> tables) {
        for (Table table : tables) {
            addTable(table);
        }
    }

    public Collection<Table> getTables() {
        return tables.values();
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schema schema = (Schema) o;

        if (name != null ? !name.equals(schema.name) : schema.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "name='" + name + '\'' +
                '}';
    }
}
