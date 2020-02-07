package com.voting.system.project.web;

import com.voting.system.project.to.RestaurantWithMenusTo;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.voting.system.project.TestData.RESTAURANTS_WITH_MENU_ON_CURRENT_DATE;
import static com.voting.system.project.web.RestaurantController.REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractControllerTest {

    @Test
    void getAllWithMenusAndDishes() throws Exception {
        final String restaurantsWithMenusOnCurrentDate = objectMapper.writeValueAsString(mapper.map(RESTAURANTS_WITH_MENU_ON_CURRENT_DATE, RestaurantWithMenusTo[].class));
        doGet(REST_URL, restaurantsWithMenusOnCurrentDate);
    }
}