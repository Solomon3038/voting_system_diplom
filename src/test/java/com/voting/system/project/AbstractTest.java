package com.voting.system.project;

import com.voting.system.project.util.mapper.OrikaMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(TimingExtension.class)
@SpringBootTest
public class AbstractTest {
    @Autowired
    protected OrikaMapper mapper;
}