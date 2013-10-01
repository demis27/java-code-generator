package org.demis.codegen.core.db.reader;

import org.demis.codegen.core.db.Schema;
import org.demis.codegen.core.db.Table;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TableReaderTest {

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
    public void readTables() {
        Assert.assertEquals(4, schema.getTables().size());
    }

    @Test
    public void readEmptyTable() {

        Table emptyTable = schema.getTable("EmptyTable");
        Assert.assertNotNull(emptyTable);
        Assert.assertEquals(emptyTable.getRemarks(), "An empty table");
        Assert.assertEquals(emptyTable.getSchema(), schema);
    }

}
