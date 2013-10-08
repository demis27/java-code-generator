package org.demis.codegen.core.generator.configuration.filter;

import junit.framework.Assert;
import org.demis.codegen.core.generator.configuration.filter.DatabaseFilter;
import org.testng.annotations.Test;

public class DatabaseFilterTest {

    @Test
    public void match() {
        DatabaseFilter filter = new DatabaseFilter("ht_.+", DatabaseFilter.DatabaseFilterTarget.TABLE);
        Assert.assertTrue(filter.match("ht_filtered"));
    }
}
