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

    @Test
    public void toUpperCaseFirst() throws Exception {
        String input = "familyTree";
        String output = DataBaseToObjectConverter.toUpperCaseFirst(input);
        Assert.assertEquals("FamilyTree", output);

        input = "f";
        output = DataBaseToObjectConverter.toUpperCaseFirst(input);
        Assert.assertEquals("F", output);

        input = "F";
        output = DataBaseToObjectConverter.toUpperCaseFirst(input);
        Assert.assertEquals("F", output);

        input = "FamilyTree";
        output = DataBaseToObjectConverter.toUpperCaseFirst(input);
        Assert.assertEquals("FamilyTree", output);
    }

    @Test
    public void toLowerCaseFirst() throws Exception {
        String input = "familyTree";
        String output = DataBaseToObjectConverter.toLowerCaseFirst(input);
        Assert.assertEquals("familyTree", output);

        input = "f";
        output = DataBaseToObjectConverter.toLowerCaseFirst(input);
        Assert.assertEquals("f", output);

        input = "F";
        output = DataBaseToObjectConverter.toLowerCaseFirst(input);
        Assert.assertEquals("f", output);

        input = "FamilyTree";
        output = DataBaseToObjectConverter.toLowerCaseFirst(input);
        Assert.assertEquals("familyTree", output);
    }
}
