package com.voting.system.project.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.voting.system.project.web.AdminDishControllerTest.ADMIN_DISH_URL_TEST;
import static com.voting.system.project.web.AdminMenuControllerTest.ADMIN_MENU_URL_TEST;
import static com.voting.system.project.web.AdminRestaurantControllerTest.ADMIN_REST_URL_TEST;
import static com.voting.system.project.web.VoteControllerTest.VOTE_URL_TEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UnauthorizedControllerTest extends AbstractControllerTest {

    @Test
    void getRestaurantUnauthorized() throws Exception {
        doGetUnauthorized(ADMIN_REST_URL_TEST);
    }

    @Test
    void getMenuUnauthorized() throws Exception {
        doGetUnauthorized(ADMIN_MENU_URL_TEST);
    }

    @Test
    void getDishUnauthorized() throws Exception {
        doGetUnauthorized(ADMIN_DISH_URL_TEST);
    }

    @Test
    void createVoteUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(VOTE_URL_TEST))
                .andExpect(status().isUnauthorized());
    }

    private void doGetUnauthorized(String url) throws Exception {
        mockMvc.perform(get(url)).andExpect(status().isUnauthorized());
    }
}
