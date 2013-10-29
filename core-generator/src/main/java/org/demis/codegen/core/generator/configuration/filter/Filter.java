package org.demis.codegen.core.generator.configuration.filter;

public class Filter {

    public enum  FilterScope {
        GLOBAL ("global"), LOCALE ("locale");

        private String value;

        private FilterScope(String value) {
            this.value = value;
        }
    }

    public enum  FilterType {
        INCLUDE ("include"), EXCLUDE ("exclude");

        private String value;

        private FilterType(String value) {
            this.value = value;
        }
    }

    private FilterScope scope = FilterScope.GLOBAL;

    private FilterType type = FilterType.EXCLUDE;

    private String expression;

    private String name;

    private boolean exclude = true;


    public Filter() {

    }

    public Filter(String name, String expression) {
        this.name = name;
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public boolean match(String value) {
        return value.matches(expression);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FilterScope getScope() {
        return scope;
    }

    public void setScope(FilterScope scope) {
        this.scope = scope;
    }

    public FilterType getType() {
        return type;
    }

    public void setType(FilterType type) {
        this.type = type;
    }
}
