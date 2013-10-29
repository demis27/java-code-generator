package org.demis.codegen.core.mapping;

import java.sql.Types;

public class SQLTypeToClassConverter {

    private static SQLTypeToClassConverter ourInstance = new SQLTypeToClassConverter();

    public static SQLTypeToClassConverter getInstance() {
        return ourInstance;
    }

    private SQLTypeToClassConverter() {
    }

    public String convert(int sqlType) {

        String javaType = null;

        switch (sqlType) {
            case Types.ARRAY:
                javaType = "unknown-ARRAY";
                break;

            case Types.BIGINT:
                javaType = "Long";
                break;
            case Types.BINARY:
                javaType = "byte[]";
                break;
            case Types.BIT:
                javaType = "Boolean";
                break;
            case Types.BLOB:
                javaType = "unknown-BLOB";
                break;
            case Types.BOOLEAN:
                javaType = "Boolean";
                break;

            case Types.CHAR:
                javaType = "String";
                break;
            case Types.CLOB:
                javaType = "unknown-CLOB";
                break;

            case Types.DATALINK:
                javaType = "unknown-DATALINK";
                break;
            case Types.DATE:
                javaType = "java.sql.Date";
                break;
            case Types.DECIMAL:
                javaType = "Double";
                break;
            case Types.DISTINCT:
                javaType = "unknown-DISTINCT";
                break;
            case Types.DOUBLE:
                javaType = "Double";
                break;

            case Types.FLOAT:
                javaType = "Double";
                break;

            case Types.INTEGER:
                javaType = "Integer";
                break;

            case Types.JAVA_OBJECT:
                javaType = "unknown-JAVA_OBJECT";
                break;

            case Types.LONGNVARCHAR:
                javaType = "unknown-LONGNVARCHAR";
                break;
            case Types.LONGVARBINARY:
                javaType = "unknown-LONGVARBINARY";
                break;
            case Types.LONGVARCHAR:
                javaType = "String";
                break;

            case Types.NCHAR:
                javaType = "String";
                break;
            case Types.NCLOB:
                javaType = "unknown-NCLOB";
                break;
            case Types.NUMERIC:
                javaType = "Double";
                break;
            case Types.NVARCHAR:
                javaType = "String";
                break;

            case Types.REAL:
                javaType = "Float";
                break;
            case Types.REF:
                javaType = "unknown-REF";
                break;
            case Types.ROWID:
                javaType = "unknown-ROWID";
                break;

            case Types.SMALLINT:
                javaType = "Short";
                break;
            case Types.SQLXML:
                javaType = "unknown-SQLXML";
                break;
            case Types.STRUCT:
                javaType = "unknown-STRUCT";
                break;

            case Types.TIME:
                javaType = "java.sql.Time";
                break;
            case Types.TIMESTAMP:
                javaType = "java.sql.Timestamp";
                break;
            case Types.TINYINT:
                javaType = "Byte";
                break;

            case Types.VARBINARY:
                javaType = "unknown-VARBINARY";
                break;
            case Types.VARCHAR:
                javaType = "String";
                break;
        }

        return javaType;
    }
}
