package org.demis.codegen.core.object;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    private String name;

    private List<Property> properties = new ArrayList<>();

    private Id id = null;

    public Entity() {
        // no op
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }
}
