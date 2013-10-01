package org.demis.codegen.core.db.reader;

import org.demis.codegen.core.db.Column;
import org.demis.codegen.core.db.Schema;
import org.demis.codegen.core.db.Table;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ColumnReaderTest {

    private Schema schema;

    @BeforeClass
    public void readSchema() {
        PostgresSchemaReader reader = new PostgresSchemaReader();

        schema = null;
        try {
            schema = reader.read("jdbc:postgresql://localhost:5432/test", "test", "test", "Test");
        } catch (DatabaseReadingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readSimpleTable() {

        Table simpleTable = schema.getTable("SimpleTable");
        Assert.assertNotNull(simpleTable);
        Assert.assertEquals(simpleTable.getRemarks(), "A simple table");
        Assert.assertEquals(simpleTable.getSchema(), schema);
        Assert.assertEquals(simpleTable.getColumns().size(), 1);

        Column simpleColumn = simpleTable.getColumn("SimpleColumn");
        Assert.assertNotNull(simpleColumn);
        Assert.assertEquals(simpleColumn.getName(), "SimpleColumn");
        Assert.assertEquals(simpleColumn.getRemarks(), "A simple column");
        Assert.assertNull(simpleColumn.getDefaultValue());
        Assert.assertEquals(simpleColumn.getTable(), simpleTable);
        Assert.assertEquals(simpleColumn.getPosition(), 1);
        Assert.assertEquals(simpleColumn.getScale(), 0);
        Assert.assertEquals(simpleColumn.getSize(), 10);
        Assert.assertEquals(simpleColumn.isPrimaryKey(), false);
        Assert.assertEquals(simpleColumn.isForeignKey(), false);
        Assert.assertEquals(simpleColumn.isNotNull(), false);
        Assert.assertEquals(simpleColumn.isNullable(), true);
        Assert.assertEquals(simpleColumn.isUnique(), false);
    }


}
