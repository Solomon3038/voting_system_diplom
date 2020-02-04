package com.voting.system.project.service;

import com.voting.system.project.AbstractTest;
import com.voting.system.project.CustomTestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(CustomTestConfiguration.class)
abstract class AbstractServiceTest extends AbstractTest {
}