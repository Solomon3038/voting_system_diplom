package com.voting.system.project.web;

import com.voting.system.project.to.RestaurantTo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.voting.system.project.TestDataHelper.RESTAURANTS_WITH_MENU_ON_CURRENT_DATE;
import static com.voting.system.project.web.RestaurantController.REST_URL;

class RestaurantControllerTest extends AbstractControllerTest {

    @Test
    void getAllWithMenusAndDishes() throws Exception {
        final List<RestaurantTo> tos = mapper.mapAsList(RESTAURANTS_WITH_MENU_ON_CURRENT_DATE, RestaurantTo.class);
        doGet(REST_URL + "?date=" + LocalDate.now(), objectMapper.writeValueAsString(tos));
    }
}