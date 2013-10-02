package org.demis.codegen.core.mapping;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DataBaseToObjectConverterTest {

    @Test
    public void convertDataBaseName() throws Exception {
        String input = "Family_Tree";
        String output = DataBaseToObjectConverter.convertDataBaseName(input);
        Assert.assertEquals("FamilyTree", output);

        input = "family_tree";
        output = DataBaseToObjectConverter.convertDataBaseName(input);
        Assert.assertEquals("FamilyTree", output);

        input = "family__tree";
        output = DataBaseToObjectConverter.convertDataBaseName(input);
        Assert.assertEquals("FamilyTree", output);

        input = "family__tree_auThor";
        output = DataBaseToObjectConverter.convertDataBaseName(input);
        Assert.assertEquals("FamilyTreeAuthor", output);
    }

}
