package org.demis.family.domain;

import java.io.Serializable;

public interface Identifiable<PK extends Serializable> {

    PK getId();

    void setId(PK id);

    boolean isIdSet();
}