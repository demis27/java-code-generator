package org.demis.codegen.core.db.reader;

import org.demis.codegen.core.db.Schema;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShemaReaderTest {

    @Test
    public void readShema() {
        PostgresSchemaReader reader = new PostgresSchemaReader();

        Schema schema = null;
        try {
            schema = reader.read("jdbc:postgresql://localhost:5432/test", "test", "test", "Test");
        } catch (DatabaseReadingException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(schema);
        Assert.assertEquals(schema.getName(), "Test");
    }

}
