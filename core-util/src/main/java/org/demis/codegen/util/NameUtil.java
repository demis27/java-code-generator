package org.demis.codegen.util;

public class NameUtil {

    public static String toUpperCaseFirst(String value) {
        if (value == null || value.length() == 0) {
            return value;
        }
        return value.substring(0, 1).toUpperCase() +  value.substring(1, value.length());
    }

    public static String toLowerCaseFirst(String value) {
        if (value == null || value.length() == 0) {
            return value;
        }
        return value.substring(0, 1).toLowerCase() +  value.substring(1, value.length());
    }

    public static String getPlural(String value) {
        if (value != null) {
            if (value.charAt(value.length() - 1) == 'y') {
                return value.substring(0, value.length() -1) + "ies";
            }
            return value + "s";
        }
        else {
            return null;
        }
    }
}
