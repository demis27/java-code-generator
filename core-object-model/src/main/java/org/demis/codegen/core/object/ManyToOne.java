package org.demis.codegen.core.object;

public class ManyToOne extends EntityRelation {

    public ManyToOne() {
        super();
    }

    @Override
    public String toString() {
        return "ManyToOne{" +
                "associatedEntity=" + (getAssociatedEntity() != null ? getAssociatedEntity().getName() : "null") +
                ", owner=" + (getOwner() != null ? getOwner().getName() : "null") +
                ", associatedProperties=" + getAssociatedProperties() +
                '}';
    }
}
