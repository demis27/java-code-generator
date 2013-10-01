package org.demis.codegen.core.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

    private final Logger logger = LoggerFactory.getLogger(Table.class);

    private String name = null;

    private String remarks = null;

    private Schema schema = null;

    private List<Column> columns = new ArrayList<>();

    private PrimaryKey primaryKey = null;

    private Map<String, ForeignKey> importedKeys = new HashMap<>();

    private Map<String, ForeignKey> exportedKeys = new HashMap<>();

    public Table() {
        // no op
    }

    public Table(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public void addColumn(Column column) {
        columns.add(column);
        column.setTable(this);
    }

    public List<Column> getColumns() {
        return new ArrayList(columns);
    }

    public Column getColumn(String columnName) {
        for (Column column : columns) {
            if (column.getName() != null && column.getName().equals(columnName)) {
                return column;
            }
        }
        return null;
    }

    public PrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(PrimaryKey primaryKey) {
        if (this.primaryKey != primaryKey) {
            this.primaryKey = primaryKey;
            primaryKey.setTable(this);
        }
    }

    public void addExportedKey(ForeignKey reference) {
        exportedKeys.put(reference.getName(), reference);
    }

    public List<ForeignKey> getExportedKeys() {
        return new ArrayList(exportedKeys.values());
    }

    public ForeignKey getExportedKey(String toReferenceName) {
        return exportedKeys.get(toReferenceName);
    }

    public void addImportedKey(ForeignKey importedKey) {
        importedKeys.put(importedKey.getName(), importedKey);
    }

    public List<ForeignKey> getImportedKeys() {
        return new ArrayList(importedKeys.values());
    }

    public ForeignKey getImportedKey(String fromReferenceName) {
        return importedKeys.get(fromReferenceName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        if (name != null ? !name.equals(table.name) : table.name != null) return false;
        if (schema != null ? !schema.equals(table.schema) : table.schema != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (schema != null ? schema.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", schema=" + schema +
                '}';
    }
}
