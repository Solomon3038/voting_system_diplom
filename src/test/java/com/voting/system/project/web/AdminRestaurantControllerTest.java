package com.voting.system.project.web;

import com.voting.system.project.to.MenuTo;
import com.voting.system.project.to.RestaurantTo;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.Arrays;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.web.AdminRestaurantController.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/test-method.html#test-method-withuserdetails
class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String ADMIN_TEST_URL = ADMIN_URL + "/";
    private static final String ADMIN_TEST_REST_URL = ADMIN_URL + REST_URL + "/";

    @Test
    @WithUserDetails(ADMIN_1_EMAIL)
    void getAllRestaurants() throws Exception {
        final String restaurants = objectMapper.writeValueAsString(mapper.map(RESTAURANTS, RestaurantTo[].class));
        doGet(ADMIN_TEST_REST_URL, restaurants);
    }

    @Test
    @WithUserDetails(ADMIN_1_EMAIL)
    void getRestaurant() throws Exception {
        final String restaurant = objectMapper.writeValueAsString(mapper.map(RESTAURANT_1, RestaurantTo.class));
        doGet(ADMIN_TEST_REST_URL + RESTAURANT_ID_1, restaurant);
    }

    @Test
    @WithUserDetails(ADMIN_1_EMAIL)
    void getMenus() throws Exception {
        final String menus = objectMapper.writeValueAsString(mapper.map(Arrays.asList(MENU_1_NOW, MENU_1), MenuTo[].class));
        doGet(ADMIN_TEST_REST_URL + RESTAURANT_ID_1 + MENU_URL, menus);
    }

    @Test
    @WithUserDetails(ADMIN_1_EMAIL)
    void getMenu() throws Exception {
        final String menus = objectMapper.writeValueAsString(mapper.map(MENU_1, MenuTo.class));
        doGet(ADMIN_TEST_REST_URL + RESTAURANT_ID_1 + MENU_URL + "/" + MENU_ID_1, menus);
    }

    private void doGet(String url, String jsonObject) throws Exception {
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonObject));
    }
}