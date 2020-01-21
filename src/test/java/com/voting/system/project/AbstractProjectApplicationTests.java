package com.voting.system.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;

//https://www.baeldung.com/spring-boot-testing
@DataJpaTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"}, config = @SqlConfig(encoding = "UTF-8"))
@Transactional
@ExtendWith(TimingExtension.class)
public abstract class AbstractProjectApplicationTests {

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
