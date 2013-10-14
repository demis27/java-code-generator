package $defaultPackageName$.service;

import $defaultPackageName$.domain.$entity.name$;
import $defaultPackageName$.dao.$entity.name$DAO;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("$entity.nameLowerFirst$Service")
public class $entity.name$ServiceImpl implements $entity.name$Service{

    private final Logger logger = LoggerFactory.getLogger($entity.name$ServiceImpl.class);

    @Autowired
    @Qualifier("$entity.nameLowerFirst$DAO")
    private $entity.name$DAO dao;

    public $entity.name$ get(String primaryKey) {
        return dao.get(primaryKey);
    }

    public void save($entity.name$ $entity.nameLowerFirst$) {

    }

    public void save(final List<$entity.name$> $entity.pluralLowerFirst$) {

    }

    public void delete($entity.name$ $entity.nameLowerFirst$) {

    }

    public void delete(final List<$entity.name$> $entity.pluralLowerFirst$) {

    }

    public $entity.name$DAO getDao() {
        return dao;
    }
}
