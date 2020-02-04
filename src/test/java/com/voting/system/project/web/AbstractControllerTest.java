package com.voting.system.project.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voting.system.project.AbstractTest;
import org.h2.tools.Server;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;

//https://spring.io/guides/gs/testing-web/
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractControllerTest extends AbstractTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected ModelMapper mapper;

    @Autowired
    protected ObjectMapper objectMapper;

    @PostConstruct
    private void postConstruct() {
        //https://stackoverflow.com/a/12447211/12805042
        final AutowireCapableBeanFactory beanFactory = webApplicationContext.getAutowireCapableBeanFactory();
        ((DefaultListableBeanFactory) beanFactory).destroySingleton("h2Server");

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }
}
