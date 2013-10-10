package $defaultPackageName$.domain;

import org.testng.annotations.Test;
import org.testng.Assert;

public class $entity.name$Test {

    // test unique primary key
    @Test
    public void newInstanceHasNoPrimaryKey() {
        $entity.name$ model = new $entity.name$();
        Assert.assertFalse(model.isIdSet());
    }
}