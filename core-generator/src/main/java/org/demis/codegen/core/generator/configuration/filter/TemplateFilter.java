package org.demis.codegen.core.generator.configuration.filter;

public interface TemplateFilter {

    public static final String SCHEMA_MODE = "schema";
    public static final String TABLE_MODE = "table";
    public static final String COLUMN_MODE = "column";

    public TemplateFilter getFilter();

    public boolean acceptMode(String mode);

//    public boolean filterTemplate(Descriptor descriptor);
}
