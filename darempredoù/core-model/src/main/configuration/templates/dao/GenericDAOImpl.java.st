package $defaultPackageName$.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GenericDAOImpl<PK, E> implements GenericDAO<PK, E> {

    private final Logger logger = LoggerFactory.getLogger(GenericDAOImpl.class);

    protected Class<E> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public GenericDAOImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
    	this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    public E get(PK primaryKey) {
        return entityManager.find(entityClass, primaryKey);
    }

    public void save(E entity) {
        entityManager.persist(entity);
    }

    public void save(final List<E> entities) {
        for (E entity: entities) {
            save(entity);
        }
    }

    public void delete(E entity) {
        entityManager.remove(entity);
    }

    public void delete(final List<E> entities) {
        for (E entity: entities) {
            delete(entity);
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}