package $defaultPackageName$.domain;

import $defaultPackageName$.domain.$entity.name$;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import $defaultPackageName$.PersistenceTestUtil;

public class $entity.name$PersistenceTest {

    @Test
    public void persistence() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("darempredou");
        Assert.assertNotNull(entityManagerFactory);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Assert.assertNotNull(entityManager);

        $entity.name$ $entity.nameLowerFirst$ = create$entity.name$();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist($entity.nameLowerFirst$);
        entityManager.flush();

        entityTransaction.commit();
    }

    public static $entity.name$ create$entity.name$() {
        $entity.name$ $entity.nameLowerFirst$ = new $entity.name$();
$entity.properties:{property|
$if(!property.id && !property.manyToOne && !property.column.nullable)$
        $entity.nameLowerFirst$.set$property.nameAsSuffix$(PersistenceTestUtil.generateRandomString($property.column.size$));
$endif$
}$
$entity.manyToOneRelations:{manyToOne|
$if(manyToOne.simple)$
        $entity.nameLowerFirst$.set$manyToOne.associatedEntity.name$($manyToOne.associatedEntity.name$PersistenceTest.create$manyToOne.associatedEntity.name$());
$endif$
}$
        return $entity.nameLowerFirst$;
    }
}
