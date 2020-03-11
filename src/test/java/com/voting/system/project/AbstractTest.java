package com.voting.system.project;

import com.voting.system.project.mapper.OrikaMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(TimingExtension.class)
@SpringBootTest(properties = "vote.h2.tcp_server.enable=false")
public class AbstractTest {
    @Autowired
    protected OrikaMapper mapper;
}