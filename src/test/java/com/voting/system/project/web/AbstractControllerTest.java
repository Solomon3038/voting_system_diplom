package com.voting.system.project.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voting.system.project.AbstractTest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "vote.h2.tcp_server.enable=false")
public abstract class AbstractControllerTest extends AbstractTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected ModelMapper mapper;

    @Autowired
    protected ObjectMapper objectMapper;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    protected void doGet(String url, String expectedJsonObject) throws Exception {
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJsonObject));
    }

    protected String doPost(String jsonObject, String url) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
    }

    protected void doPut(String jsonObject, String url) throws Exception {
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    protected void doDelete(String url) throws Exception {
        mockMvc.perform(delete(url))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    protected void doGetNotExist(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().isUnprocessableEntity());
    }

    protected void doPostUnprocessable(String jsonObject, String url, ResultMatcher status) throws Exception {
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject))
                .andExpect(status);
    }
}
