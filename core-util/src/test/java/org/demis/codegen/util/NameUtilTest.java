package org.demis.codegen.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class NameUtilTest {
    @Test
    public void toUpperCaseFirst() throws Exception {
        String input = "familyTree";
        String output = NameUtil.toUpperCaseFirst(input);
        Assert.assertEquals("FamilyTree", output);

        input = "f";
        output = NameUtil.toUpperCaseFirst(input);
        Assert.assertEquals("F", output);

        input = "F";
        output = NameUtil.toUpperCaseFirst(input);
        Assert.assertEquals("F", output);

        input = "FamilyTree";
        output = NameUtil.toUpperCaseFirst(input);
        Assert.assertEquals("FamilyTree", output);
    }

    @Test
    public void toLowerCaseFirst() throws Exception {
        String input = "familyTree";
        String output = NameUtil.toLowerCaseFirst(input);
        Assert.assertEquals("familyTree", output);

        input = "f";
        output = NameUtil.toLowerCaseFirst(input);
        Assert.assertEquals("f", output);

        input = "F";
        output = NameUtil.toLowerCaseFirst(input);
        Assert.assertEquals("f", output);

        input = "FamilyTree";
        output = NameUtil.toLowerCaseFirst(input);
        Assert.assertEquals("familyTree", output);
    }

    @Test
    public void getPlural() {
        String input = "Family";
        String output = NameUtil.getPlural(input);
        Assert.assertEquals(output, "Families");

        input = "Test";
        output = NameUtil.getPlural(input);
        Assert.assertEquals(output, "Tests");
    }
}
