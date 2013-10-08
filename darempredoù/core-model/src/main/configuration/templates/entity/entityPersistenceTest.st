package $defaultPackageName$.domain;

import $defaultPackageName$.domain.$entity.name$;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class $entity.name$PersistenceTest {

    @Test
    public void persistence() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("darempredou");
        Assert.assertNotNull(entityManagerFactory);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Assert.assertNotNull(entityManager);

        $entity.name$ $entity.nameLowerFirst$ = new $entity.name$();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist($entity.nameLowerFirst$);
        entityManager.flush();

        entityTransaction.commit();
    }
}
