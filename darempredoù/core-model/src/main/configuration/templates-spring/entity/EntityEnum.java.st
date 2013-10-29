package $defaultPackageName$.domain;

public enum $entity.name$ {


$entity.id.properties:{property|
    private $property.javaType$ $property.name$ = null;
}$

$entity.properties:{property|
    private $property.javaType$ $property.name$;
}$

    private $entity.name$($entity.properties:{property|$property.javaType$ $property.name$,}; {$entity.id.properties:{property|$property.javaType$ $property.name$}$)

    }
}