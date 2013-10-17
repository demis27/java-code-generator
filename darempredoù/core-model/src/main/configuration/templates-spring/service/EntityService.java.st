package $defaultPackageName$.service;

import $defaultPackageName$.domain.$entity.name$;

import java.util.List;

public interface $entity.name$Service {

    public $entity.name$ get(String primaryKey);

    public $entity.name$ getEager(String primaryKey);

    public void save($entity.name$ $entity.nameLowerFirst$);

    public void save(final List<$entity.name$> $entity.pluralLowerFirst$);

    public void delete($entity.name$ $entity.nameLowerFirst$);

    public void delete(final List<$entity.name$> $entity.pluralLowerFirst$);

}
