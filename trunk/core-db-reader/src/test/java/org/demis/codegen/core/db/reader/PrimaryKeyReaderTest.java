package org.demis.codegen.core.db.reader;

import org.demis.codegen.core.db.Column;
import org.demis.codegen.core.db.PrimaryKey;
import org.demis.codegen.core.db.Schema;
import org.demis.codegen.core.db.Table;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PrimaryKeyReaderTest {

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
    public void readSimplePrimaryKeyTable() {

        Table primaryTable = schema.getTable("PrimaryTable");
        Assert.assertNotNull(primaryTable);
        Assert.assertEquals(primaryTable.getRemarks(), "A simple table with a primary key");
        Assert.assertEquals(primaryTable.getSchema(), schema);
        Assert.assertEquals(primaryTable.getColumns().size(), 2);

        PrimaryKey primaryKey = primaryTable.getPrimaryKey();
        Assert.assertNotNull(primaryKey);
        Assert.assertEquals(primaryKey.getTable(), primaryTable);
        Assert.assertEquals(primaryKey.getName(), "SimplePrimaryKey");
        Assert.assertEquals(primaryKey.getColumns().size(), 1);

        Column tableColumn = primaryTable.getColumn("SimplePrimaryKey");
        Column primaryColumn = primaryKey.getColumn("SimplePrimaryKey");
        Assert.assertEquals(tableColumn, primaryColumn);
        Assert.assertTrue(tableColumn.isPrimaryKey());
        Assert.assertTrue(primaryColumn.isPrimaryKey());

        Assert.assertEquals(primaryKey.getTable(), primaryTable);
        Assert.assertEquals(primaryKey.getColumn("SimplePrimaryKey"), primaryKey.getColumnAs(1));
    }

    @Test
    public void readComplexPrimaryKeyTable() {

        Table primaryTable = schema.getTable("ComplexPrimaryTable");
        Assert.assertNotNull(primaryTable);
        Assert.assertEquals(primaryTable.getRemarks(), "A table with a complex primary key");
        Assert.assertEquals(primaryTable.getSchema(), schema);
        Assert.assertEquals(primaryTable.getColumns().size(), 3);


        PrimaryKey primaryKey = primaryTable.getPrimaryKey();
        Assert.assertNotNull(primaryKey);
        Assert.assertEquals(primaryKey.getTable(), primaryTable);
        Assert.assertEquals(primaryKey.getName(), "PrimaryKey");
        Assert.assertEquals(primaryKey.getColumns().size(), 2);

        Column firstPrimaryKeyColumn = primaryKey.getColumnAs(1);
        Column secondPrimaryKeyColumn = primaryKey.getColumnAs(2);
        Assert.assertNotNull(firstPrimaryKeyColumn);
        Assert.assertNotNull(secondPrimaryKeyColumn);
        Assert.assertEquals(primaryTable.getColumn("PrimaryKeyPartOne"), firstPrimaryKeyColumn);
        Assert.assertEquals(primaryTable.getColumn("PrimaryKeyPartTwo"), secondPrimaryKeyColumn);
        Assert.assertTrue(firstPrimaryKeyColumn.isPrimaryKey());
        Assert.assertTrue(secondPrimaryKeyColumn.isPrimaryKey());
    }
}
