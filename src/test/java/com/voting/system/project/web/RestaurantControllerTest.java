package com.voting.system.project.web;

import com.voting.system.project.to.RestaurantWithMenusTo;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.voting.system.project.TestData.RESTAURANTS_WITH_MENU_ON_CURRENT_DATE;
import static com.voting.system.project.web.AdminRestaurantController.REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractControllerTest {

    @Test
    void getAllWithMenusAndDishes() throws Exception {
        final String restaurantsWithMenusOnCurrentDate = objectMapper.writeValueAsString(mapper.map(RESTAURANTS_WITH_MENU_ON_CURRENT_DATE, RestaurantWithMenusTo[].class));

        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(restaurantsWithMenusOnCurrentDate));
    }
}