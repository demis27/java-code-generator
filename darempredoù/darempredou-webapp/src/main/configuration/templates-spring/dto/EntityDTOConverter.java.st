package $defaultPackageName$.dto;

import $defaultPackageName$.domain.$entity.name$;
import $defaultPackageName$.service.$entity.name$Page;
$if(entity.oneToManyRelations)$
$entity.oneToManyRelations:{oneToMany|
$if(oneToMany.simple)$
import $defaultPackageName$.domain.$oneToMany.associatedEntity.name$;
$endif$
}$
$endif$

import java.util.ArrayList;
import java.util.List;

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
        dto.setUri("/rest/$entity.nameLowerFirst$/" + entity.getId() );

        return dto;
    }

    public static $entity.name$DTO convertToAccess($entity.name$ entity) {
        $entity.name$URIDTO access = new $entity.name$URIDTO();
        access.setId(entity.getId());
        return access;
    }

    public static $entity.name$DTOPage convertPage($entity.name$Page entities) {
        $entity.name$DTOPage dto = new $entity.name$DTOPage();
        dto.hasNext(entities.isHasNext());
        dto.hasPrevious(entities.isHasPrevious());
        dto.setPageNumber(entities.getNumber());
        dto.setPageSize(entities.getSize());
        dto.setTotalElements(entities.getTotalElements());
        dto.setTotalPage(entities.getTotalPage());

        List<$entity.name$DTO> elements = new ArrayList<$entity.name$DTO>();

        for ($entity.name$ entity: entities.getElements()) {
            elements.add(convert(entity));
        }
        dto.setElements(elements);

        // next and previous uri
        if (entities.isHasNext()) {
            dto.setNextUri("/rest/$entity.nameLowerFirst$/?pageNumber=" + (entities.getNumber() + 1) + "&pageSize=" +  entities.getSize());
        }
        if (entities.isHasPrevious()) {
            dto.setPreviousUri("/rest/$entity.nameLowerFirst$/?pageNumber=" + (entities.getNumber() - 1) + "&pageSize=" +  entities.getSize());
        }

        return dto;
    }

    public static void update$entity.name$($entity.name$FullDTO source, $entity.name$ target) {
        // raw properties
$entity.properties:{property|
$if(!property.id && !property.manyToOne)$
        target.set$property.nameAsSuffix$(source.get$property.nameAsSuffix$());
$endif$
}$

    }
}