package org.demis.codegen.core.mapping;

import org.demis.codegen.core.db.Column;
import org.demis.codegen.core.db.Schema;
import org.demis.codegen.core.db.Table;
import org.demis.codegen.core.object.Entity;
import org.demis.codegen.core.object.EntityPackage;
import org.demis.codegen.core.object.Property;

import java.util.HashMap;
import java.util.Map;

public class Mapping {

    private Map<Object, Object> mapping = new HashMap<Object, Object>();

    private static Mapping ourInstance = new Mapping();

    public static Mapping getInstance() {
        return ourInstance;
    }

    private Mapping() {
    }

    public void addMapping(Schema schema, EntityPackage entityPackage) {
        mapping.put(schema, entityPackage);
        mapping.put(entityPackage, schema);
    }

    public void addMapping(Table table, Entity entity) {
        mapping.put(table, entity);
        mapping.put(entity, table);
    }


    public void addMapping(Column column, Property property) {
        mapping.put(column, property);
        mapping.put(property, column);
    }

    public Table getTable(Entity entity) {
        return (Table)(mapping.get(entity));
    }

    public Entity getEntity(Table table) {
        return (Entity)(mapping.get(table));
    }

    public Column getColumn(Property property) {
        return (Column)(mapping.get(property));
    }

    public Property getProperty(Column column) {
        return (Property)(mapping.get(column));
    }

    public Schema getSchema(EntityPackage entityPackage) {
        return (Schema)(mapping.get(entityPackage));
    }

    public EntityPackage getEntityPackage(Schema schema) {
        return (EntityPackage)(mapping.get(schema));
    }
}
