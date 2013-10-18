package $defaultPackageName$.dto;

import $defaultPackageName$.domain.$entity.name$;
$if(entity.oneToManyRelations)$
$entity.oneToManyRelations:{oneToMany|
$if(oneToMany.simple)$
import $defaultPackageName$.domain.$oneToMany.associatedEntity.name$;
$endif$
}$
$endif$

public class $entity.name$DTOConverter  {

    public static $entity.name$ convert($entity.name$FullDTO dto) {
        $entity.name$ entity = new $entity.name$();
        // Primary Key
$if(entity.id.simple)$
$entity.id.properties:{property|
        entity.setId(dto.getId());
}$
$endif$
        // raw properties
$entity.properties:{property|
$if(!property.id && !property.manyToOne)$
        entity.set$property.nameAsSuffix$(dto.get$property.nameAsSuffix$());
$endif$
}$

        return entity;
    }

    public static $entity.name$DTO convert ($entity.name$ entity) {
        $entity.name$FullDTO dto = new $entity.name$FullDTO();
        // Primary Key
$if(entity.id.simple)$
$entity.id.properties:{property|
        dto.setId(entity.getId());
}$
$endif$
        // raw properties
$entity.properties:{property|
$if(!property.id && !property.manyToOne)$
        dto.set$property.nameAsSuffix$(entity.get$property.nameAsSuffix$());
$endif$
}$
$if(entity.oneToManyRelations)$
        // one to many attributes methods
$entity.oneToManyRelations:{oneToMany|
$if(oneToMany.simple && oneToMany.composition)$
        for ($oneToMany.associatedEntity.name$ $oneToMany.associatedEntity.nameLowerFirst$ : entity.get$oneToMany.associatedEntity.pluralUpperFirst$()) {
            dto.add$oneToMany.associatedEntity.name$($oneToMany.associatedEntity.name$DTOConverter.convertToAccess($oneToMany.associatedEntity.nameLowerFirst$));
        \}
$endif$
}$
$endif$
        return dto;
    }

    public static $entity.name$DTO convertToAccess($entity.name$ entity) {
        $entity.name$URIDTO access = new $entity.name$URIDTO();
        access.setId(entity.getId());
        return access;
    }
}