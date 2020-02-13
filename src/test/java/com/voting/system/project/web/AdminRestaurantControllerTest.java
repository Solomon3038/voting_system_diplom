package com.voting.system.project.web;

import com.voting.system.project.model.Restaurant;
import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.to.RestaurantTo;
import com.voting.system.project.to.RestaurantWithMenusTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.TestDataTo.getNewRestaurantTo;
import static com.voting.system.project.TestDataTo.getUpdatedRestaurantTo;
import static com.voting.system.project.util.RestaurantTestUtil.checkSave;
import static com.voting.system.project.util.RestaurantTestUtil.checkSaveWithMenusAndDishes;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static com.voting.system.project.web.AdminRestaurantController.ADMIN_REST_URL;

//https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/test-method.html#test-method-withuserdetails
@WithUserDetails(ADMIN_1_EMAIL)
class AdminRestaurantControllerTest extends AbstractControllerTest {

    public static final String ADMIN_REST_URL_TEST = ADMIN_REST_URL + "/";

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void getAllRestaurants() throws Exception {
        final String restaurants = objectMapper.writeValueAsString(mapper.map(RESTAURANTS, RestaurantTo[].class));
        doGet(ADMIN_REST_URL_TEST, restaurants);
    }

    @Test
    void getRestaurant() throws Exception {
        final String restaurant = objectMapper.writeValueAsString(mapper.map(RESTAURANT_1, RestaurantTo.class));
        doGet(ADMIN_REST_URL_TEST + RESTAURANT_ID_1, restaurant);
    }

    @Test
    void createWithLocation() throws Exception {
        RestaurantTo newRestaurant = getNewRestaurantTo();
        String restaurant = objectMapper.writeValueAsString(newRestaurant);
        String result = doPost(restaurant, ADMIN_REST_URL_TEST);
        Restaurant created = objectMapper.readValue(result, Restaurant.class);
        checkSave(created);
        newRestaurant.setId(created.getId());
        assertMatch(restaurantService.get(created.getId()), newRestaurant);
    }

    @Test
    void update() throws Exception {
        RestaurantTo updatedRestaurant = getUpdatedRestaurantTo(RESTAURANT_1);
        String restaurant = objectMapper.writeValueAsString(updatedRestaurant);
        doPut(restaurant, ADMIN_REST_URL_TEST + RESTAURANT_ID_1);
        assertMatch(restaurantService.get(RESTAURANT_ID_1), updatedRestaurant);
    }

    @Test
    void createWithLocationFull() throws Exception {
        Restaurant newRestaurant = getNewRestaurantWithMenuAndDishes();
        String restaurant = objectMapper.writeValueAsString(mapper.map(newRestaurant, RestaurantWithMenusTo.class));
        String result = doPost(restaurant, ADMIN_REST_URL_TEST + "/full");
        RestaurantWithMenusTo created = objectMapper.readValue(result, RestaurantWithMenusTo.class);
        checkSaveWithMenusAndDishes(mapper.map(created, Restaurant.class));
        newRestaurant.setId(created.getId());
        assertMatch(restaurantService.get(created.getId()), newRestaurant);
    }
}