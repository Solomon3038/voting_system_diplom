package com.voting.system.project.web;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.voting.system.project.TestDataHelper.ADMIN_1_EMAIL;
import static com.voting.system.project.TestDataHelper.USER_1_EMAIL;
import static com.voting.system.project.web.AdminDishControllerTest.ADMIN_DISH_URL_TEST;
import static com.voting.system.project.web.AdminMenuItemControllerTest.ADMIN_MENU_URL_TEST;
import static com.voting.system.project.web.AdminRestaurantControllerTest.ADMIN_REST_URL_TEST;
import static com.voting.system.project.web.VoteControllerTest.VOTE_URL_TEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityControllerTest extends AbstractControllerTest {

    @Test
    void getRestaurantUnauthorized() throws Exception {
        doGet(ADMIN_REST_URL_TEST, status().isUnauthorized());
    }

    @Test
    @WithUserDetails(USER_1_EMAIL)
    void getRestaurantForbidden() throws Exception {
        doGet(ADMIN_REST_URL_TEST, status().isForbidden());
    }

    @Test
    void getMenuUnauthorized() throws Exception {
        doGet(ADMIN_MENU_URL_TEST, status().isUnauthorized());
    }

    @Test
    @WithUserDetails(USER_1_EMAIL)
    void getMenuForbidden() throws Exception {
        doGet(ADMIN_MENU_URL_TEST, status().isForbidden());
    }

    @Test
    void getDishUnauthorized() throws Exception {
        doGet(ADMIN_DISH_URL_TEST, status().isUnauthorized());
    }

    @Test
    @WithUserDetails(USER_1_EMAIL)
    void getDishForbidden() throws Exception {
        doGet(ADMIN_DISH_URL_TEST, status().isForbidden());
    }

    @Test
    void createVoteUnauthorized() throws Exception {
        doPost(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(ADMIN_1_EMAIL)
    void createVoteForbidden() throws Exception {
        doPost(status().isForbidden());
    }

    private void doGet(String url, ResultMatcher status) throws Exception {
        mockMvc.perform(get(url)).andExpect(status);
    }

    private void doPost(ResultMatcher status) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(VOTE_URL_TEST))
                .andExpect(status);
    }
}