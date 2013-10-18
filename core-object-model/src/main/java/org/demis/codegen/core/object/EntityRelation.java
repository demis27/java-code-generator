package org.demis.codegen.core.object;

import org.demis.codegen.core.db.ForeignKey;

import java.util.HashMap;
import java.util.Map;

public class EntityRelation {
    private Entity associatedEntity = null;
    private Entity owner;
    private Map<Property, Property> associatedProperties = new HashMap<>();
    private String name;
    private ForeignKey foreignKey;

    public Entity getAssociatedEntity() {
        return associatedEntity;
    }

    public void setAssociatedEntity(Entity associatedEntity) {
        this.associatedEntity = associatedEntity;
    }

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public Map<Property, Property> getAssociatedProperties() {
        return associatedProperties;
    }

    public void setAssociatedProperties(Map<Property, Property> associatedProperties) {
        this.associatedProperties = associatedProperties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ForeignKey getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(ForeignKey foreignKey) {
        this.foreignKey = foreignKey;
    }

    public void addAssociatedProperties(Property source, Property target) {
        associatedProperties.put(source, target);
    }

    public Property getTargetProperty(Property property) {
        return associatedProperties.get(property);
    }

    public boolean isSimple() {
        if (associatedProperties != null)
            return associatedProperties.size() > 0;
        else
            return false;
    }

    public Property getSimpleSource() {
        if (isSimple()) {
            return associatedProperties.keySet().iterator().next();
        }
        else {
            return null;
        }
    }
}
