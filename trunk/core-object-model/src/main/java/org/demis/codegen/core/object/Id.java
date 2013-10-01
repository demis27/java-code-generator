package org.demis.codegen.core.object;

import java.util.ArrayList;
import java.util.List;

public class Id {

    private List<Property> properties = new ArrayList<>();

    private Entity owner;

    private String name;

    public Id() {
        // no op
    }

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public void addProperty(Property property) {
        this.properties.add(property);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
