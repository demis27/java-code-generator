package $defaultPackageName$.dto;

import $defaultPackageName$.domain.$entity.name$;

public class $entity.name$DTOConverter  {

    public static $entity.name$ convert($entity.name$DTO dto) {
        return new $entity.name$();
    }

    public static $entity.name$DTO convert ($entity.name$ entity) {
        return new $entity.name$DTO();
    }
}