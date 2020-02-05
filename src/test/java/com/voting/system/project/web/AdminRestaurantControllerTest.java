package com.voting.system.project.web;

import com.voting.system.project.to.RestaurantTo;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.web.AdminRestaurantController.ADMIN_URL;
import static com.voting.system.project.web.AdminRestaurantController.REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/test-method.html#test-method-withuserdetails
class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String ADMIN_TEST_URL = ADMIN_URL + REST_URL + "/";

    @Test
    @WithUserDetails(ADMIN_1_EMAIL)
    void getRestaurant() throws Exception {
        final String restaurant = objectMapper.writeValueAsString(mapper.map(RESTAURANT_1, RestaurantTo.class));

        mockMvc.perform(get(ADMIN_TEST_URL + RESTAURANT_ID_1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(restaurant));
    }
}