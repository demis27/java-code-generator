package $defaultPackageName$.dao;

import $defaultPackageName$.domain.$entity.name$;
import $defaultPackageName$.domain.$entity.name$PersistenceTest;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class $entity.name$DAOTest {

    private EntityManager entityManager;

    private $entity.name$DAOImpl dao;

    @BeforeTest
    public void beforeTest() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("darempredou");
        Assert.assertNotNull(entityManagerFactory);

        entityManager = entityManagerFactory.createEntityManager();
        Assert.assertNotNull(entityManager);

        dao = new $entity.name$DAOImpl();
        Assert.assertNotNull(dao);
        dao.setEntityManager(entityManager);
    }

    @Test
    public void saveEntity() {
        $entity.name$ $entity.nameLowerFirst$ = $entity.name$PersistenceTest.create$entity.name$();
        Assert.assertNotNull($entity.nameLowerFirst$);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        dao.save($entity.nameLowerFirst$);
        entityManager.flush();

        transaction.commit();
    }
}