package org.demis.codegen.core.db.reader;

import org.demis.codegen.core.db.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GenericSchemaReader {

    private final Logger logger = LoggerFactory.getLogger(GenericSchemaReader.class);

    protected static void cleanConnection(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
            }
        }
    }

    public Schema read(String url, String login, String password, String shema) throws DatabaseReadingException {
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        databaseConfiguration.setUrl(url);
        databaseConfiguration.setUser(login);
        databaseConfiguration.setPassword(password);
        databaseConfiguration.setSchemaName(shema);

        return read(databaseConfiguration);
    }

    public Schema read(DatabaseConfiguration configuration) throws DatabaseReadingException {

        Schema schema = readSchema(configuration);
        if (schema != null) {
            readTables(configuration, schema);
            readColumns(configuration, schema);
            readPrimaryKeys(configuration, schema);
            readForeignKeys(configuration, schema);
        }
        return schema;
    }

    public Schema readSchema(DatabaseConfiguration configuration) throws DatabaseReadingException {
        Schema schema = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getConnection(configuration);
            DatabaseMetaData metaData = connection.getMetaData();
            resultSet = metaData.getSchemas();
            while (resultSet.next()) {
                String schemaFound = resultSet.getString("TABLE_SCHEM");
                if (schemaFound.equalsIgnoreCase(configuration.getSchemaName())) {
                    schema = new Schema(configuration.getSchemaName());
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseReadingException("Error when reading the Schema", ex);
        } finally {
            cleanConnection(connection, null, resultSet);
        }
        return schema;
    }

    public void readTables(DatabaseConfiguration configuration, Schema schema) {
        Map<String, Table> tables = new HashMap<>();
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(configuration);
            DatabaseMetaData metaData = connection.getMetaData();
            // read tables
            logger.info("read tables for schema #" + schema);
            resultSet = metaData.getTables(
                    null,
                    schema.getName(),
                    null,
                    new String[]{"TABLE"});
            while (resultSet.next()) {
                Table table = new Table(resultSet.getString("TABLE_NAME"));
                table.setRemarks(resultSet.getString("REMARKS"));
                tables.put(table.getName(), table);
                logger.info("read table #" + table);
            }
        } catch (SQLException ex) {
            logger.error("Error when reading schema's table " + configuration.getSchemaName(), ex);
        } finally {
            cleanConnection(connection, null, resultSet);
        }
        schema.addTables(new ArrayList<>(tables.values()));
    }

    public void readColumns(DatabaseConfiguration configuration, Schema schema) {
        Connection connection = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection(configuration);
            DatabaseMetaData metaData = connection.getMetaData();
            for (Table table : schema.getTables()) {
                logger.info("read columns for table #" + table + " on schema #" + schema);
                // columns
                resultSet = metaData.getColumns(
                        null,
                        configuration.getSchemaName(),
                        table.getName(),
                        null);
                while (resultSet.next()) {
                    Column column = new Column(table, resultSet.getString("COLUMN_NAME"));
                    // type
                    column.setType(resultSet.getInt("DATA_TYPE"));
                    // column size
                    column.setSize(resultSet.getInt("COLUMN_SIZE"));
                    // scale
                    column.setScale(resultSet.getInt("DECIMAL_DIGITS"));
                    // nullable
                    int nullable = resultSet.getInt("NULLABLE");
                    column.setNotNull(nullable != DatabaseMetaData.columnNullable);
                    // remarks
                    column.setRemarks(resultSet.getString("REMARKS"));
                    // ordinal position
                    int position = resultSet.getInt("ORDINAL_POSITION");
                    column.setPosition(position);

                    table.addColumn(column);
                    logger.info("read column #" + column);
                }
                resultSet.close();
            }
        } catch (SQLException ex) {
            logger.error("Error when reading schema's table's column", ex);
        } finally {
            cleanConnection(connection, null, resultSet);
        }
    }

    public void readPrimaryKeys(DatabaseConfiguration configuration, Schema schema) {
        Connection connection = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection(configuration);
            DatabaseMetaData metaData = connection.getMetaData();
            for (Table table : schema.getTables()) {
                // primary key
                resultSet = metaData.getPrimaryKeys(
                        null,
                        configuration.getSchemaName(),
                        table.getName());
                PrimaryKey primaryKey = new PrimaryKey();
                while (resultSet.next()) {
                    Column column = table.getColumn(resultSet.getString("COLUMN_NAME"));
                    column.setPrimaryKey(true);
                    int order = resultSet.getInt("KEY_SEQ");
                    primaryKey.addColumn(column, order);
                    primaryKey.setName(resultSet.getString("PK_NAME"));
                }
                logger.info("read primaryKey #" + primaryKey);
                if (primaryKey.getName() != null) {
                    table.setPrimaryKey(primaryKey);
                }
                resultSet.close();
            }
        } catch (SQLException ex) {
            logger.error("Error when reading primary keys", ex);
        } finally {
            cleanConnection(connection, null, resultSet);
        }
    }

    public void readForeignKeys(DatabaseConfiguration configuration, Schema schema) {
        Connection connection = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection(configuration);
            DatabaseMetaData metaData = connection.getMetaData();
            for (Table table : schema.getTables()) {
                // foreign key
                resultSet = metaData.getExportedKeys(
                        null,
                        configuration.getSchemaName(),
                        table.getName());
                ForeignKey reference;
                while (resultSet.next()) {
                    String importedTableName = resultSet.getString("FKTABLE_NAME");
                    String importedColumnName = resultSet.getString("FKCOLUMN_NAME");
                    String exportedTableName = resultSet.getString("PKTABLE_NAME");
                    String exportedColumnName = resultSet.getString("PKCOLUMN_NAME");
                    logger.info("reading foreign key #" + importedTableName + "" + importedColumnName
                            + " -> " + exportedTableName + "." + exportedColumnName);
                    // Primary table
                    Table exportedTable = schema.getTable(exportedTableName);
                    // Primary column
                    Column exportedColumn = exportedTable.getColumn(exportedColumnName);
                    // Foreign table
                    Table importedTable = schema.getTable(importedTableName);
                    // Foreign column
                    Column importedColumn = importedTable.getColumn(importedColumnName);
                    // foreign key name
                    String referenceName = resultSet.getString("FK_NAME");


                    reference = exportedTable.getExportedKey(referenceName);
                    if (reference == null) {
                        reference = new ForeignKey();
                        reference.setName(referenceName);
                        reference.setExportedTable(exportedTable);
                        reference.setImportedTable(importedTable);
                    }
                    else {
                        reference.setName(referenceName);
                    }
                    reference.addReferencedColumn(importedColumn, exportedColumn);

                    importedTable.addImportedKey(reference);
                    exportedTable.addExportedKey(reference);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error when reading foreign keys", ex);
        } finally {
            cleanConnection(connection, null, resultSet);
        }
    }

    protected Connection getConnection(DatabaseConfiguration configuration) throws SQLException {
        if (configuration.getUser() != null) {
            return DriverManager.getConnection(configuration.getUrl(), configuration.getUser(), configuration.getPassword());
        } else {
            return DriverManager.getConnection(configuration.getUrl());
        }
    }
}
