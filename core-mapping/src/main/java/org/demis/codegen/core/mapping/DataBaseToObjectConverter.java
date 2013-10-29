package org.demis.codegen.core.mapping;

import org.demis.codegen.core.object.EntityPackage;
import org.demis.codegen.util.NameUtil;
import org.demis.codegen.core.db.*;
import org.demis.codegen.core.object.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DataBaseToObjectConverter {

    private final Logger logger = LoggerFactory.getLogger(DataBaseToObjectConverter.class);

    private static DataBaseToObjectConverter ourInstance = new DataBaseToObjectConverter();

    public static DataBaseToObjectConverter getInstance() {
        return ourInstance;
    }

    private DataBaseToObjectConverter() {
    }

    public EntityPackage convertSchema(Schema schema) {
        logger.info("Convert schema = " + schema);
        EntityPackage entityEntityPackage = new EntityPackage();

        List<Entity> entities = new ArrayList<>();

        for (Table table: schema.getTables()) {
            Entity entity = convertTable(table);
            entities.add(entity);
            entity.setProperties(convertColumns(table));
            if (table.getPrimaryKey() != null) {
                entity.setId(convertPrimaryKey(table.getPrimaryKey()));
            }

        }

        for (Table table: schema.getTables()) {
            for (ForeignKey foreignKey: table.getExportedKeys()) {
                convertToManyToOneReference(foreignKey);
            }
            for (ForeignKey foreignKey: table.getImportedKeys()) {
                convertToOneToManyReference(foreignKey);
            }
        }
        entityEntityPackage.setEntities(entities);
        return entityEntityPackage;
    }

    public Entity convertTable(Table table) {
        logger.info("Convert table = " + table);
        Entity entity = new Entity();

        entity.setName(convertDataBaseName(table.getName()));
        entity.setTable(table);
        Mapping.getInstance().addMapping(table, entity);

        logger.info("Conver table to entity = " + entity);
       return entity;
    }


    public List<Property> convertColumns(Table table) {
        List<Property> properties = new ArrayList<>();
        for (Column column : table.getColumns()) {
            Property property = convertColumn(column);
            properties.add(property);
            Mapping.getInstance().getEntity(table).addProperty(property);
            Mapping.getInstance().addMapping(column, property);
        }

        return properties;
    }
    public Property convertColumn(Column column) {
        logger.info("Convert column = " + column);
        Property property = new Property();
        // Process property name
        String propertyName = convertDataBaseName(column.getName(), !column.isPrimaryKey());
        propertyName = propertyName.substring(0,1).toLowerCase() + propertyName.substring(1,propertyName.length());
        property.setName(propertyName);
        // Process java type
        property.setJavaType(SQLTypeToClassConverter.getInstance().convert(column.getType()));
        property.setColumn(column);
        Mapping.getInstance().addMapping(column, property);

        logger.info("Converted column = " + column + " to property : " + property);
        return property;
    }

    public Id convertPrimaryKey(PrimaryKey primaryKey) {
        Id id = new Id();
        String name;
        if (primaryKey.nbColumn() > 1) {
            name = convertDataBaseName(primaryKey.getTable().getName()) + "PrimaryKey";
        }
        else {
            name = convertDataBaseName(primaryKey.getColumnAs(1).getName(), false);
        }

        name = NameUtil.toLowerCaseFirst(name);

        for (Column column : primaryKey.getColumns()) {
            column.setForeignKey(true);
            Mapping.getInstance().getProperty(column).setId(true);
            id.addProperty(Mapping.getInstance().getProperty(column));
        }
        id.setName(name);

        Entity classDescriptor = Mapping.getInstance().getEntity(primaryKey.getTable());
        classDescriptor.setId(id);

        return id;
    }

    public OneToMany convertToOneToManyReference(ForeignKey reference) {
        OneToMany oneToMany = new OneToMany();
        oneToMany.setAssociatedEntity(Mapping.getInstance().getEntity(reference.getImportedTable()));
        oneToMany.setOwner(Mapping.getInstance().getEntity(reference.getExportedTable()));

        // from properties
        for (Column fromColumn : reference.getImportedColumns()) {
            Column toColumn = reference.getExportedColumn(fromColumn);

            Property leftProperty = Mapping.getInstance().getProperty(fromColumn);
            Property rightProperty = Mapping.getInstance().getProperty(toColumn);
            oneToMany.addAssociatedProperties(rightProperty, leftProperty);
        }
        // name
        if (reference.getExportedTable() != null) {
            oneToMany.setName(NameUtil.toLowerCaseFirst((convertDataBaseName(reference.getName()))));
        }
        oneToMany.setForeignKey(reference);

        Entity entity = Mapping.getInstance().getEntity(reference.getExportedTable());
        entity.addOneToManyRelation(oneToMany);

        logger.info("'from' reference #" + reference + " converted to relation #" + oneToMany);
        return oneToMany;
    }

    public ManyToOne convertToManyToOneReference(ForeignKey reference) {
        ManyToOne manyToOne = new ManyToOne();
        manyToOne.setOwner(Mapping.getInstance().getEntity(reference.getImportedTable()));
        manyToOne.setAssociatedEntity(Mapping.getInstance().getEntity(reference.getExportedTable()));

        // from properties
        for (Column fromColumn : reference.getImportedColumns()) {
            Column toColumn = reference.getExportedColumn(fromColumn);

            Property leftProperty = Mapping.getInstance().getProperty(fromColumn);
            leftProperty.setManyToOne(true);
            Property rightProperty = Mapping.getInstance().getProperty(toColumn);
            manyToOne.addAssociatedProperties(leftProperty, rightProperty);
        }
        // name
        if (reference.getImportedColumns().size() == 1) {
            Column fromColumn = reference.getImportedColumns().iterator().next();
            //manyToOne.setName(toLowerCaseFirst(convertDataBaseName(fromColumn.getName())));
        }
        manyToOne.setForeignKey(reference);

        Entity entity = Mapping.getInstance().getEntity(reference.getImportedTable());
        entity.addManyToOneRelation(manyToOne);

        logger.info("'to' reference #" + reference + " converted to relation #" + manyToOne);
        return manyToOne;
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

}
