package com.voting.system.project.web;

import com.voting.system.project.to.RestaurantWithMenusTo;
import org.junit.jupiter.api.Test;

import static com.voting.system.project.TestDataHelper.RESTAURANTS_WITH_MENU_ON_CURRENT_DATE;
import static com.voting.system.project.web.RestaurantController.REST_URL;

class RestaurantControllerTest extends AbstractControllerTest {

    @Test
    void getAllWithMenusAndDishes() throws Exception {
        final String restaurantsWithMenusOnCurrentDate = objectMapper.writeValueAsString(mapper.map(RESTAURANTS_WITH_MENU_ON_CURRENT_DATE, RestaurantWithMenusTo[].class));
        doGet(REST_URL, restaurantsWithMenusOnCurrentDate);
    }
}