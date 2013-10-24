package $defaultPackageName$.service;

import $defaultPackageName$.domain.$entity.name$;

import java.util.List;

public interface $entity.name$Service {

    public $entity.name$ get(Integer primaryKey);

    public $entity.name$ getEager(Integer primaryKey);

    public $entity.name$Page getPage(int page, int offset);

    public void save($entity.name$ $entity.nameLowerFirst$);

    public void save(final List<$entity.name$> $entity.pluralLowerFirst$);

    public void delete($entity.name$ $entity.nameLowerFirst$);

    public void delete(final List<$entity.name$> $entity.pluralLowerFirst$);

}
