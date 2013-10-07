package org.demis.codegen.core.db.reader;

import org.demis.codegen.core.db.ForeignKey;
import org.demis.codegen.core.db.Schema;
import org.demis.codegen.core.db.Table;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ForeignKeyReaderTest {
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
    public void readSimpleForeignKeyTable() {
        Table foreignKeyTable = schema.getTable("ForeignKeyTable");
        Assert.assertNotNull(foreignKeyTable);
        Assert.assertEquals(foreignKeyTable.getRemarks(), "a table with a foreign key on PrimaryKeyTable");
        Assert.assertEquals(foreignKeyTable.getSchema(), schema);
        Assert.assertEquals(foreignKeyTable.getColumns().size(), 2);

        Assert.assertEquals(foreignKeyTable.getImportedKeys().size(), 1);
        ForeignKey foreignKeyImported = foreignKeyTable.getImportedKey("ForeignKeyTable.SimpleForeignKey") ;
        Assert.assertNotNull(foreignKeyImported);

        Table primaryTable = schema.getTable("PrimaryTable");
        Assert.assertNotNull(primaryTable);
        Assert.assertEquals(primaryTable.getExportedKeys().size(), 1);
        ForeignKey foreignKeyExported = primaryTable.getExportedKey("ForeignKeyTable.SimpleForeignKey") ;
        Assert.assertNotNull(foreignKeyExported);

        Assert.assertEquals(foreignKeyImported.getImportedTable(), foreignKeyTable);
        Assert.assertEquals(foreignKeyImported.getExportedTable(), primaryTable);

        Assert.assertEquals(foreignKeyExported.getImportedTable(), foreignKeyTable);
        Assert.assertEquals(foreignKeyExported.getExportedTable(), primaryTable);
    }

    @Test
    public void readMultiForeignKeyTable() {
        Table foreignKeyTableB = schema.getTable("MultiFkB");
        Assert.assertNotNull(foreignKeyTableB);
        Assert.assertEquals(foreignKeyTableB.getImportedKeys().size(), 2);

        Table foreignKeyTableC = schema.getTable("MultiFkC");
        Assert.assertNotNull(foreignKeyTableB);
        Assert.assertEquals(foreignKeyTableC.getImportedKeys().size(), 1);

        Table primaryKeyTableA = schema.getTable("MultiFkA");
        Assert.assertNotNull(primaryKeyTableA);
        Assert.assertEquals(primaryKeyTableA.getExportedKeys().size(), 3);

    }
}
