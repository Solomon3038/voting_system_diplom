package com.voting.system.project.repository;

import com.voting.system.project.AbstractTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//https://www.baeldung.com/spring-boot-testing
@DataJpaTest
abstract class AbstractRepositoryTest extends AbstractTest {

    @Autowired
    protected EntityManager em;

    //https://stackoverflow.com/a/4400236
    protected <T> void checkEntityFieldLoadingType(Class<T> tClass, String attributeName, boolean isLazy) {
        final PersistenceUnitUtil unitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
        final T entity = em.find(tClass, 1);
        if (isLazy) {
            assertFalse(unitUtil.isLoaded(entity, attributeName));
        } else {
            assertTrue(unitUtil.isLoaded(entity, attributeName));
        }
    }
}
