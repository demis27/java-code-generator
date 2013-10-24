package $defaultPackageName$.service;

import $defaultPackageName$.domain.$entity.name$;
import $defaultPackageName$.repository.$entity.name$Repository;
$if(entity.oneToManyRelations)$
$entity.oneToManyRelations:{oneToMany|
$if(oneToMany.simple)$
import $defaultPackageName$.domain.$oneToMany.associatedEntity.name$;
$endif$
}$
$endif$

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class $entity.name$ServiceImpl implements $entity.name$Service{

    private final Logger logger = LoggerFactory.getLogger($entity.name$ServiceImpl.class);

    @Resource
    private $entity.name$Repository $entity.nameLowerFirst$Repository;

    @Transactional
    @Override
    public $entity.name$ get(Integer primaryKey) {
        return $entity.nameLowerFirst$Repository.findOne(primaryKey);
    }

    @Transactional
    @Override
    public $entity.name$ getEager(Integer primaryKey) {
        $entity.name$ $entity.nameLowerFirst$ = $entity.nameLowerFirst$Repository.findByIdEagerly(primaryKey);
        return $entity.nameLowerFirst$;
    }

    @Transactional
    @Override
    public $entity.name$Page getPage(int pageNumber, int size) {
        Page<$entity.name$> all = $entity.nameLowerFirst$Repository.findAll(new PageRequest(pageNumber, size));
        $entity.name$Page page = new $entity.name$Page();
        page.setSize(all.getSize());
        page.setTotalElements(all.getTotalElements());
        page.setHasPrevious(all.hasPreviousPage());
        page.setTotalPage(all.getTotalPages());
        page.setNumber(all.getNumber());
        page.setHasNext(all.hasNextPage());
        page.setElements(all.getContent());

        return page;
    }

    @Transactional
    @Override
    public void save($entity.name$ $entity.nameLowerFirst$) {

    }

    @Transactional
    @Override
    public void save(final List<$entity.name$> $entity.pluralLowerFirst$) {

    }

    @Transactional
    @Override
    public void delete($entity.name$ $entity.nameLowerFirst$) {

    }

    @Transactional
    @Override
    public void delete(final List<$entity.name$> $entity.pluralLowerFirst$) {

    }

    public $entity.name$Repository get$entity.name$Repository() {
        return $entity.nameLowerFirst$Repository;
    }

    public void set$entity.name$Repository($entity.name$Repository $entity.nameLowerFirst$Repository) {
        this.$entity.nameLowerFirst$Repository = $entity.nameLowerFirst$Repository;
    }
}
