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
                javaType = "java.lang.Long";
                break;
            case Types.BINARY:
                javaType = "byte[]";
                break;
            case Types.BIT:
                javaType = "java.lang.Boolean";
                break;
            case Types.BLOB:
                javaType = "unknown-BLOB";
                break;
            case Types.BOOLEAN:
                javaType = "java.lang.Boolean";
                break;

            case Types.CHAR:
                javaType = "java.lang.String";
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
                javaType = "java.lang.Double";
                break;
            case Types.DISTINCT:
                javaType = "unknown-DISTINCT";
                break;
            case Types.DOUBLE:
                javaType = "java.lang.Double";
                break;

            case Types.FLOAT:
                javaType = "java.lang.Double";
                break;

            case Types.INTEGER:
                javaType = "java.lang.Integer";
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
                javaType = "java.lang.String";
                break;

            case Types.NCHAR:
                javaType = "java.lang.String";
                break;
            case Types.NCLOB:
                javaType = "unknown-NCLOB";
                break;
            case Types.NUMERIC:
                javaType = "java.lang.Double";
                break;
            case Types.NVARCHAR:
                javaType = "java.lang.String";
                break;

            case Types.REAL:
                javaType = "java.lang.Float";
                break;
            case Types.REF:
                javaType = "unknown-REF";
                break;
            case Types.ROWID:
                javaType = "unknown-ROWID";
                break;

            case Types.SMALLINT:
                javaType = "java.lang.Short";
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
                javaType = "java.lang.Byte";
                break;

            case Types.VARBINARY:
                javaType = "unknown-VARBINARY";
                break;
            case Types.VARCHAR:
                javaType = "java.lang.String";
                break;
        }

        return javaType;
    }
}
