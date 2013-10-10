package org.demis.codegen.core.object;

import java.util.ArrayList;
import java.util.List;

public class EntityPackage {

    private String name;

    private List<Entity> entities = new ArrayList<>();

    public EntityPackage() {

    }

    public EntityPackage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityPackage aEntityPackage = (EntityPackage) o;

        if (name != null ? !name.equals(aEntityPackage.name) : aEntityPackage.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "EntityPackage{" +
                "name='" + name + '\'' +
                '}';
    }
}
