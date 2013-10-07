package org.demis.codegen.core.db;

import java.util.*;

public class ForeignKey {

    private String name = null;

    private Map<ColumnReference, ColumnReference> columnsMapping = new HashMap<>();

    private Table importedTable = null;

    private Table exportedTable = null;

    public ForeignKey() {
        // no op
    }

    public ForeignKey(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addReferencedColumn(ColumnReference importedReference, ColumnReference exportedReference) {
        columnsMapping.put(importedReference, exportedReference);
    }

    public void addReferencedColumn(Column importedColumn, Column exportedColumn) {
        ColumnReference importedReference = new ColumnReference(importedColumn);
        ColumnReference exportedReference = new ColumnReference(exportedColumn);
        columnsMapping.put(importedReference, exportedReference);
        importedColumn.setNoref(false);
        exportedColumn.setNoref(false);
        importedTable = importedColumn.getTable();
        exportedTable = exportedColumn.getTable();
    }

    public Column getExportedColumn(Column importedColumn) {
        for (ColumnReference importedReference: columnsMapping.keySet()) {
            if (importedColumn.equals(importedReference.getColumn())) {
                return columnsMapping.get(importedReference).getColumn();
            }
        }
        return null;
    }

    public Column getExportedColumn(String importedColumnName) {
        for (ColumnReference importedReference: columnsMapping.keySet()) {
            if (importedColumnName.equals(importedReference.getColumn().getName())) {
                return columnsMapping.get(importedReference).getColumn();
            }
        }
        return null;
    }

    public Column getForeignKeyColumnIfUnic() {
        return columnsMapping.values().iterator().next().getColumn();
    }

    public Column getPrimaryKeyColumnIfUnic() {
        return columnsMapping.keySet().iterator().next().getColumn();
    }

    public Collection<Column> getImportedColumns() {
        List<Column> columns = new ArrayList<>();
        for (ColumnReference importedReference: columnsMapping.keySet()) {
            columns.add(importedReference.getColumn());
        }

        return columns;
    }

    public Collection<ColumnReference> getImportedColumnReferences() {
        return columnsMapping.keySet();
    }

    public Collection<Column> getExportedColumns() {
        List<Column> columns = new ArrayList<>();
        for (ColumnReference importedReference: columnsMapping.keySet()) {
            columns.add(columnsMapping.get(importedReference).getColumn());
        }

        return columns;
    }

    public Collection<ColumnReference> getExportedColumnReferences() {
        return columnsMapping.values();
    }

    public int getNbColumn() {
        return columnsMapping.size();
    }

    public Table getImportedTable() {
        return importedTable;
    }

    public void setImportedTable(Table importedTable) {
        this.importedTable = importedTable;
    }

    public Table getExportedTable() {
        return exportedTable;
    }

    public void setExportedTable(Table exportedTable) {
        this.exportedTable = exportedTable;
    }

    @Override
    public String toString() {
        return "ForeignKey{" +
                "name='" + name + '\'' +
                ", columnsMapping=" + columnsMapping +
                ", importedTable=" + (importedTable != null ? importedTable.getName() : "null") +
                ", exportedTable=" + (exportedTable != null ? exportedTable.getName() : "null") +
                '}';
    }
}
