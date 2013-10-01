package org.demis.codegen.core.mapping;

import org.demis.codegen.core.db.*;
import org.demis.codegen.core.object.Entity;
import org.demis.codegen.core.object.Id;
import org.demis.codegen.core.object.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DataBaseToObjectConverter {

    private static DataBaseToObjectConverter ourInstance = new DataBaseToObjectConverter();

    public static DataBaseToObjectConverter getInstance() {
        return ourInstance;
    }

    private DataBaseToObjectConverter() {
    }

    public List<Entity> convertSchema(Schema schema) {
        List<Entity> classes = new ArrayList<Entity>();

        for (Table table: schema.getTables()) {
            Entity classDescriptor = convertTable(table);
            classes.add(classDescriptor);
            convertColumns(table);
            if (table.getPrimaryKey() != null) {
                convertPrimaryKey(table.getPrimaryKey());
            }

        }

        for (Table table: schema.getTables()) {
            for (ForeignKey foreignKey: table.getExportedKeys()) {
//                convertFromReference(foreignKey);
            }
            for (ForeignKey foreignKey: table.getImportedKeys()) {
//                convertToReference(foreignKey);
            }
        }

        return classes;
    }

    public Entity convertTable(Table table) {
        Entity entity = new Entity();

        entity.setName(convertDataBaseName(table.getName()));
        Mapping.getInstance().addMapping(table, entity);

       return entity;
    }


    public List<Property> convertColumns(Table table) {
        List<Property> properties = new ArrayList<Property>();
        for (Column column : table.getColumns()) {
            Property property = convertColumn(column);
            properties.add(property);
            Mapping.getInstance().getEntity(table).addProperty(property);
            Mapping.getInstance().addMapping(column, property);
        }

        return properties;
    }
    public Property convertColumn(Column column) {
        Property property = new Property();
        // Process property name
        String propertyName = convertDataBaseName(column.getName(), !column.isPrimaryKey());
        propertyName = propertyName.substring(0,1).toLowerCase() + propertyName.substring(1,propertyName.length());
        property.setName(propertyName);
        // Process java type
        property.setJavaType(SQLTypeToClassConverter.getInstance().convert(column.getType()));
        Mapping.getInstance().addMapping(column, property);

        return property;
    }

    public Id convertPrimaryKey(PrimaryKey primaryKey) {
        Id id = new Id();
        // ident name
        String name = null;
        String className = null;
        if (primaryKey.nbColumn() > 1) {
            name = convertDataBaseName(primaryKey.getTable().getName()) + "PrimaryKey";
            className = name;
        }
        else {
            name = convertDataBaseName(primaryKey.getColumnAs(1).getName(), false);
            className = Mapping.getInstance().getProperty(primaryKey.getColumnAs(1)).getJavaType();
        }

        name = toLowerCaseFirst(name);

        for (Column column : primaryKey.getColumns()) {
            column.setForeignKey(true);
            id.addProperty(Mapping.getInstance().getProperty(column));
        }
        id.setName(name);

        Entity classDescriptor = Mapping.getInstance().getEntity(primaryKey.getTable());
        classDescriptor.setId(id);

        return id;
    }

    public static String convertDataBaseName(String name) {
        return convertDataBaseName(name, true);
    }

    public static String convertDataBaseName(String name, boolean removeIdinName) {
        String baseName = name.toLowerCase();
        StringTokenizer tokenizer = new StringTokenizer(baseName, "_");
        StringBuffer buffer = new StringBuffer("");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (!(token.equalsIgnoreCase("id") && removeIdinName && !tokenizer.hasMoreTokens())) {
                buffer.append(token.substring(0, 1).toUpperCase());
                buffer.append(token.substring(1, token.length()).toLowerCase());
            }
        }
        return buffer.toString();
    }

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
}
