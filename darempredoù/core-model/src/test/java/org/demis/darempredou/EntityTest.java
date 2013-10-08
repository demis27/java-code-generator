package org.demis.darempredou;

import org.demis.darempredou.domain.Users;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EntityTest {

    @Test
    public void persistence() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("darempredou");
        Assert.assertNotNull(entityManagerFactory);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Assert.assertNotNull(entityManager);

        Users userGroup = new Users();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(userGroup);
        entityManager.flush();

        entityTransaction.commit();
    }
}
