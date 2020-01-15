package com.voting.system.project;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

//https://www.baeldung.com/spring-boot-testing
@DataJpaTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"}, config = @SqlConfig(encoding = "UTF-8"))
@Transactional
public abstract class ProjectApplicationTests {

}
