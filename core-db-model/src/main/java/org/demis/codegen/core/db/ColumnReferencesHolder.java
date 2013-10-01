package org.demis.codegen.core.db;

import java.util.ArrayList;
import java.util.List;

public class ColumnReferencesHolder {

    private List<ColumnReference> referencedColumns = new ArrayList<>();

    public void addColumnReference(ColumnReference reference) {
        referencedColumns.add(reference);
    }

    public void addColumn(Column column, int order) {
        ColumnReference reference = new ColumnReference();
        reference.setColumn(column);
        reference.setOrder(order);
        referencedColumns.add(reference);
    }

    public List<ColumnReference> getColumnReferences() {
        return referencedColumns;
    }

    public List<Column> getColumns() {
        List<Column> result = new ArrayList<>();

        for (ColumnReference reference : referencedColumns) {
            if (reference.getColumn() != null) {
                result.add(reference.getColumn());
            }
        }

        return result;
    }

    public int nbColumn() {
        return referencedColumns.size();
    }

    public Column getColumnAs(int pos) {
        for (ColumnReference reference : referencedColumns) {
            if (reference.getOrder() == pos) {
                return reference.getColumn();
            }
        }
        return null;
    }

    public Column getColumn(String columnName) {
        for (ColumnReference reference : referencedColumns) {
            if (reference.getColumn().getName().equals(columnName)) {
                return reference.getColumn();
            }
        }
        return null;
    }
}
