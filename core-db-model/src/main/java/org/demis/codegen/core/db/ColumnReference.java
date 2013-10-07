package org.demis.codegen.core.db;

public class ColumnReference {

    private Column column;

    private int order;

    public ColumnReference() {
        // no op
    }

    public ColumnReference(Column column) {
        this.column = column;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColumnReference that = (ColumnReference) o;

        if (order != that.order) return false;
        if (column != null ? !column.equals(that.column) : that.column != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = column != null ? column.hashCode() : 0;
        result = 31 * result + order;
        return result;
    }

    @Override
    public String toString() {
        return "ColumnReference{" +
                "column=" + (column != null ? column.getName() : "null")+
                ", order=" + order +
                '}';
    }
}