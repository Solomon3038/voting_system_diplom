package com.voting.system.project.repository;

import com.voting.system.project.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;

//https://www.baeldung.com/spring-boot-testing
@DataJpaTest
abstract class AbstractRepositoryTest extends AbstractTest {

    @Autowired
    protected EntityManager em;

    //https://stackoverflow.com/a/4400236
    protected <T> void checkEntityFieldLoadingType(Class<T> tClass, String attributeName, boolean isLazy) {
        PersistenceUnitUtil unitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
        T entity = em.find(tClass, 1);
        if (isLazy) {
            Assertions.assertFalse(unitUtil.isLoaded(entity, attributeName));
        } else {
            Assertions.assertTrue(unitUtil.isLoaded(entity, attributeName));
        }
    }
}
