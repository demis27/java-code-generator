package org.demis.codegen.core.generator.configuration.filter;

public class Filter {

    private String expression;

    public Filter() {

    }

    public Filter(String expression) {
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
}
