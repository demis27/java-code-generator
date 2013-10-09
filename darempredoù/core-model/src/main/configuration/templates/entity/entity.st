package $defaultPackageName$.domain;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "$entity.table.name$")
$if(entity.id.simple)$
$entity.id.properties:{property|
public class $entity.name$ implements Identifiable<$property.javaType$>, Serializable {
}$
$else$
$endif$
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger($entity.name$.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Primary Key
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
$if(entity.id.simple)$
$entity.id.properties:{property|
    private $property.javaType$ $property.name$ = null;
}$
$endif$
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // raw properties
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
$entity.properties:{property|
$if(!property.id && !property.manyToOne)$
    private $property.javaType$ $property.name$ = null;
$endif$
}$
$if(entity.manyToOneRelations)$
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Many to one
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
$entity.manyToOneRelations:{manyToOne|
$if(manyToOne.simple)$
    private $manyToOne.associatedEntity.name$ $manyToOne.simpleSource.name$ = null;
$endif$
}$
$endif$
$if(entity.oneToManyRelations)$
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // One to many
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
$entity.oneToManyRelations:{oneToMany|
$if(oneToMany.simple)$
    private List<$oneToMany.associatedEntity.name$> $oneToMany.associatedEntity.pluralLowerFirst$ = new ArrayList<>();
$endif$
}$
$endif$

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public $entity.name$() {
        // no op
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Primary Key methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
$if(entity.id.simple)$
$entity.id.properties:{property|
    @Override
    @Id
    @Column(name = "$property.column.name$", precision = $property.column.size$)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    public String getId() {
        return $property.name$;
    \}

    @Override
    public void setId(String id) {
        this.$property.name$ = id;
    \}

    @Override
    @Transient
    public boolean isIdSet() {
        return $property.name$ != null;
    \}
}$
$endif$

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // raw attribute methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
$entity.properties:{property|
$if(!property.id && !property.manyToOne)$
    @Column(name = "$property.column.name$", nullable = $property.column.nullable$, unique = $property.column.unique$, length = $property.column.size$)
    public $property.javaType$ get$property.nameAsSuffix$() {
        return $property.name$;
    \}

    public void set$property.nameAsSuffix$($property.javaType$ $property.name$) {
        this.$property.name$ = $property.name$;
    \}
$endif$
}$

$if(entity.manyToOneRelations)$
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // many to one attributes methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
$entity.manyToOneRelations:{manyToOne|
$if(manyToOne.simple)$
$if(manyToOne.simpleSource.column.notNull)$
    @NotNull
$endif$
    @JoinColumn(name = "$manyToOne.simpleSource.column.name$", nullable = false)
    @ManyToOne(cascade = { PERSIST, MERGE \}, fetch = LAZY)
    public $manyToOne.associatedEntity.name$ get$manyToOne.associatedEntity.name$() {
        return $manyToOne.simpleSource.name$;
    \}

    public void set$manyToOne.associatedEntity.name$($manyToOne.associatedEntity.name$ $manyToOne.simpleSource.name$) {
        this.$manyToOne.simpleSource.name$ = $manyToOne.simpleSource.name$;
    \}
$endif$
}$
$endif$

$if(entity.oneToManyRelations)$
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // one to many attributes methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
$entity.oneToManyRelations:{oneToMany|
$if(oneToMany.simple)$
    @OneToMany(mappedBy = "$oneToMany.owner.nameLowerFirst$", orphanRemoval = true, cascade = ALL)
    public List<$oneToMany.associatedEntity.name$> get$oneToMany.associatedEntity.pluralUpperFirst$() {
        return $oneToMany.associatedEntity.pluralLowerFirst$;
    \}

    public void set$oneToMany.associatedEntity.pluralUpperFirst$(List<$oneToMany.associatedEntity.name$> $oneToMany.associatedEntity.pluralLowerFirst$) {
        this.$oneToMany.associatedEntity.pluralLowerFirst$ = $oneToMany.associatedEntity.pluralLowerFirst$;
    \}

    public boolean add$oneToMany.associatedEntity.name$($oneToMany.associatedEntity.name$ $oneToMany.associatedEntity.nameLowerFirst$) {
        if (get$oneToMany.associatedEntity.pluralUpperFirst$().add($oneToMany.associatedEntity.nameLowerFirst$)) {
            $oneToMany.associatedEntity.nameLowerFirst$.set$oneToMany.owner.name$(this);
            return true;
        \}
        return false;
    \}

    public boolean remove$oneToMany.associatedEntity.name$($oneToMany.associatedEntity.name$ $oneToMany.associatedEntity.nameLowerFirst$) {
        if (get$oneToMany.associatedEntity.pluralUpperFirst$().remove($oneToMany.associatedEntity.nameLowerFirst$)) {
            $oneToMany.associatedEntity.nameLowerFirst$.set$oneToMany.owner.name$(null);
            return true;
        \}
        return false;
    \}
$endif$
}$
$endif$
}


