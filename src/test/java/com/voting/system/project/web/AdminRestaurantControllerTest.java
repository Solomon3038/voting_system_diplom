package com.voting.system.project.web;

import com.voting.system.project.to.MenuTo;
import com.voting.system.project.to.RestaurantTo;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.Arrays;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.web.AdminRestaurantController.*;
import static com.voting.system.project.web.RestaurantController.REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/test-method.html#test-method-withuserdetails
class AdminRestaurantControllerTest extends AbstractControllerTest {

    public static final String ADMIN_REST_URL_TEST = ADMIN_REST_URL + "/";

    @Test
    @WithUserDetails(ADMIN_1_EMAIL)
    void getAllRestaurants() throws Exception {
        final String restaurants = objectMapper.writeValueAsString(mapper.map(RESTAURANTS, RestaurantTo[].class));
        doGet(ADMIN_REST_URL_TEST, restaurants);
    }

    @Test
    @WithUserDetails(ADMIN_1_EMAIL)
    void getRestaurant() throws Exception {
        final String restaurant = objectMapper.writeValueAsString(mapper.map(RESTAURANT_1, RestaurantTo.class));
        doGet(ADMIN_REST_URL_TEST + RESTAURANT_ID_1, restaurant);
    }
}