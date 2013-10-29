package org.demis.codegen.core.generator.configuration.filter;

public class DatabaseFilter extends Filter {

    public enum  DatabaseFilterTarget {
        SCHEMA ("schema"), TABLE ("table"), COLUMN("column");

        private String value;

        private DatabaseFilterTarget(String value) {
            this.value = value;
        }
    }

    private DatabaseFilterTarget target;

    public DatabaseFilter() {

    }

    public DatabaseFilter(String name, String expression, DatabaseFilterTarget target) {
        super(name, expression);
        this.target = target;
    }

    public DatabaseFilterTarget getTarget() {
        return target;
    }

    public void setTarget(DatabaseFilterTarget target) {
        this.target = target;
    }
}
