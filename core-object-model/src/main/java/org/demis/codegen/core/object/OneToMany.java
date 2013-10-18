package org.demis.codegen.core.object;

public class OneToMany extends EntityRelation{

    private boolean composition = false;

    public OneToMany() {
        super();
    }

    public boolean isComposition() {
        return composition;
    }

    public void setComposition(boolean composition) {
        this.composition = composition;
    }

    @Override
    public String toString() {
        return "OneToMany{" +
                "associatedEntity=" + (getAssociatedEntity() != null ? getAssociatedEntity().getName() : "null") +
                ", owner=" + (getOwner() != null ? getOwner().getName() : "null") +
                ", associatedProperties=" + getAssociatedProperties() +
                ", composition=" + isComposition() +
                '}';
    }
}
