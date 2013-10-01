package org.demis.codegen.core.generator.configuration.filter;


/**
 * @version 1.0
 * @author <a href="mailto:demis27@demis27.net">Stéphane kermabon</a>
 */
public class MultipleColumnPrimaryKeyFilter implements TemplateFilter {

    private final static MultipleColumnPrimaryKeyFilter instance = new MultipleColumnPrimaryKeyFilter();

    public MultipleColumnPrimaryKeyFilter() {
        // no op
    }

    @Override
    public TemplateFilter getFilter() {
        return instance;
    }

    @Override
    public boolean acceptMode(String mode) {
        return TABLE_MODE.equalsIgnoreCase(mode);
    }
/*
    @Override
    public boolean filterTemplate(Descriptor descriptor) {
        if (descriptor != null && descriptor instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor)descriptor;
            return classDescriptor.getIdent().haveOneProperty();
        }
        else {
            return true;
        }
    }
*/
}
