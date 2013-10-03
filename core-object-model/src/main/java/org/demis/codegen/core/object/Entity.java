package org.demis.codegen.core.object;

import org.demis.codegen.util.NameUtil;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    private String name;

    private List<Property> properties = new ArrayList<>();

    private Id id = null;

    private List<OneToMany> oneToManyRelations = new ArrayList<>();

    private List<ManyToOne> manyToOneRelations = new ArrayList<>();

    public Entity() {
        // no op
    }

    public String getName() {
        return name;
    }

    public String getNameLowerFirst() {
        return NameUtil.toLowerCaseFirst(name);
    }

    public String getNameUpperFirst() {
        return NameUtil.toUpperCaseFirst(name);
    }

    public String getPluralLowerFirst() {
        return NameUtil.toLowerCaseFirst(NameUtil.getPlural(name));
    }

    public String getPluralUpperFirst() {
        return NameUtil.toUpperCaseFirst(NameUtil.getPlural(name));
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public boolean haveProperties() {
        if (properties != null) {
            return properties.size() > 0;
        }
        else {
            return false;
        }
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

    public List<OneToMany> getOneToManyRelations() {
        return oneToManyRelations;
    }

    public boolean haveOneToManyRelations() {
        if (oneToManyRelations != null) {
            return oneToManyRelations.size() > 0;
        }
        else {
            return false;
        }
    }

    public void setOneToManyRelations(List<OneToMany> oneToManyRelations) {
        this.oneToManyRelations = oneToManyRelations;
    }

    public void addOneToManyRelation(OneToMany oneToManyRelation) {
        this.oneToManyRelations.add(oneToManyRelation);
    }

    public List<ManyToOne> getManyToOneRelations() {
        return manyToOneRelations;
    }

    public boolean haveManyToOneRelations() {
        if (manyToOneRelations != null) {
            return manyToOneRelations.size() > 0;
        }
        else {
            return false;
        }
    }

    public void setManyToOneRelations(List<ManyToOne> manyToOneRelations) {
        this.manyToOneRelations = manyToOneRelations;
    }

    public void addManyToOneRelation(ManyToOne manyToOneRelations) {
        this.manyToOneRelations.add(manyToOneRelations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (name != null ? !name.equals(entity.name) : entity.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", properties=" + properties +
                ", id=" + id +
                '}';
    }
}
