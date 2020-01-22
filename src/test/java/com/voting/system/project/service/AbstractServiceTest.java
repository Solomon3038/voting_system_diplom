package com.voting.system.project.service;

import com.voting.system.project.AbstractTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(ServiceConfiguration.class)
abstract class AbstractServiceTest extends AbstractTest {
}