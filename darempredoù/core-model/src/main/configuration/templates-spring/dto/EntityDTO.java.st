package $defaultPackageName$.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class $entity.name$DTO  {

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
    private List<$oneToMany.associatedEntity.name$> $oneToMany.associatedEntity.pluralLowerFirst$ = new ArrayList<$oneToMany.associatedEntity.name$>();
$endif$
}$
$endif$

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public $entity.name$DTO() {
        // no op
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Primary Key methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
$if(entity.id.simple)$
$entity.id.properties:{property|
    public String getId() {
        return $property.name$;
    \}

    public void setId(String id) {
        this.$property.name$ = id;
    \}
}$
$endif$

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // raw attribute methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
$entity.properties:{property|
$if(!property.id && !property.manyToOne)$
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


