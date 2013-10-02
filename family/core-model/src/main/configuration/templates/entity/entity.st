package $defaultPackageName$.domain ;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.Id;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "$entity.name$")
$if(entity.id.simple)$
$entity.id.properties:{property|
public class $entity.name$ implements Identifiable<$property.javaType$>, Serializable {
}$
$else$
$endif$
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger($entity.name$.class);

    // Primary Key
$if(entity.id.simple)$
$entity.id.properties:{property|
    private $property.javaType$ $property.name$ = null;
}$
$endif$
    // raw properties
$entity.properties:{property|
$if(!property.id && !property.manyToOne)$
    private $property.javaType$ $property.name$ = null;
$endif$
}$
    // Many to one
$entity.manyToOneRelations:{manyToOne|
$if(manyToOne.simple)$
    private $manyToOne.associatedEntity.name$ $manyToOne.simpleSource.name$ = null;
$endif$
}$


    // Constructor
    public $entity.name$() {
        // no op
    }

    // Primary Key methods
$if(entity.id.simple)$
$entity.id.properties:{property|
    @Override
    @Id
    @Column(name = "$property.name$", precision = $property.column.size$)
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

    // raw attribute methods
$entity.properties:{property|
$if(!property.id && !property.manyToOne)$
    @Column(name = "$property.name$", nullable = $property.column.nullable$, unique = $property.column.unique$, length = $property.column.size$)
    public $property.javaType$ get$property.nameAsSuffix$() {
        return $property.name$;
    \}

    public void set$property.nameAsSuffix$($property.javaType$ $property.name$) {
        this.$property.name$ = $property.name$;
    \}

$endif$
}$
}