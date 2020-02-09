package com.voting.system.project.service;

import com.voting.system.project.AbstractTest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "vote.h2.tcp_server.enable=false")
abstract class AbstractServiceTest extends AbstractTest {

    @Autowired
    protected ModelMapper mapper;
}