package com.voting.system.project.web;

import com.voting.system.project.model.Restaurant;
import com.voting.system.project.service.DishService;
import com.voting.system.project.service.MenuService;
import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.to.RestaurantTo;
import com.voting.system.project.to.RestaurantWithMenusTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.TestDataTo.*;
import static com.voting.system.project.util.RestaurantTestUtil.*;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static com.voting.system.project.web.AdminRestaurantController.ADMIN_REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/test-method.html#test-method-withuserdetails
@WithUserDetails(ADMIN_1_EMAIL)
class AdminRestaurantControllerTest extends AbstractControllerTest {

    public static final String ADMIN_REST_URL_TEST = ADMIN_REST_URL + "/";

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private DishService dishService;

    @Test
    void getAll() throws Exception {
        final String restaurants = objectMapper.writeValueAsString(mapper.map(RESTAURANTS, RestaurantTo[].class));
        doGet(ADMIN_REST_URL_TEST, restaurants);
    }

    @Test
    void get() throws Exception {
        final String restaurant = objectMapper.writeValueAsString(mapper.map(RESTAURANT_1, RestaurantTo.class));
        doGet(ADMIN_REST_URL_TEST + RESTAURANT_ID_1, restaurant);
    }

    @Test
    void getNotExist() throws Exception {
        doGetNotExist(ADMIN_REST_URL_TEST + NOT_EXIST_ID);
    }

    @Test
    void createWithLocation() throws Exception {
        final RestaurantTo newRestaurant = getNewRestaurantTo();
        final String restaurant = objectMapper.writeValueAsString(newRestaurant);
        final String result = doPost(restaurant, ADMIN_REST_URL_TEST);
        final RestaurantWithMenusTo created = objectMapper.readValue(result, RestaurantWithMenusTo.class);
        checkSave(mapper.map(created, Restaurant.class));
        newRestaurant.setId(created.getId());
        assertMatch(restaurantService.get(created.getId()), newRestaurant);
    }

    @Test
    void createWithLocationWithMenu() throws Exception {
        final RestaurantWithMenusTo newRestaurant = getNewRestaurantWithMenuTo();
        final String restaurant = objectMapper.writeValueAsString(newRestaurant);
        final String result = doPost(restaurant, ADMIN_REST_URL_TEST);
        final RestaurantWithMenusTo created = objectMapper.readValue(result, RestaurantWithMenusTo.class);
        checkSaveWithMenu(mapper.map(created, Restaurant.class));
        newRestaurant.setId(created.getId());
        assertMatch(restaurantService.get(created.getId()), newRestaurant);
    }

    @Test
    void createWithLocationWithMenuAndDishes() throws Exception {
        final RestaurantWithMenusTo newRestaurant = getNewRestaurantWithMenuAndDishesTo();
        final String restaurant = objectMapper.writeValueAsString(newRestaurant);
        final String result = doPost(restaurant, ADMIN_REST_URL_TEST);
        final RestaurantWithMenusTo created = objectMapper.readValue(result, RestaurantWithMenusTo.class);
        checkSaveWithMenuAndDishes(mapper.map(created, Restaurant.class));
        newRestaurant.setId(created.getId());
        assertMatch(restaurantService.get(created.getId()), newRestaurant);
    }

    @Test
    void createExistError() throws Exception {
        final String restaurant = objectMapper.writeValueAsString(new Restaurant(RESTAURANT_ID_1, "New Name", "New Address"));
        doPostErr(restaurant, ADMIN_REST_URL_TEST, status().isUnprocessableEntity());
        Assertions.assertThrows(NotExistException.class, () -> restaurantService.get(RESTAURANT_ID_NEXT));
    }

    @Test
    void createDuplicateDataError() throws Exception {
        final String restaurant = objectMapper.writeValueAsString(new Restaurant(null, RESTAURANT_1.getName(), RESTAURANT_1.getAddress()));
        doPostErr(restaurant, ADMIN_REST_URL_TEST, status().isConflict());
        Assertions.assertThrows(NotExistException.class, () -> restaurantService.get(RESTAURANT_ID_NEXT));
    }

    @Test
    void createInvalidDataError() throws Exception {
        final String restaurant = objectMapper.writeValueAsString(getInvalidNewRestaurantWithMenuAndDishesTo());
        doPostErr(restaurant, ADMIN_REST_URL_TEST, status().isUnprocessableEntity());
        Assertions.assertThrows(NotExistException.class, () -> restaurantService.get(RESTAURANT_ID_NEXT));
        Assertions.assertThrows(NotExistException.class, () -> menuService.get(MENU_ID_NEXT, RESTAURANT_ID_NEXT));
        Assertions.assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_NEXT, MENU_ID_NEXT));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void update() throws Exception {
        final RestaurantTo updatedRestaurant = getUpdatedRestaurantTo(RESTAURANT_1);
        final String restaurant = objectMapper.writeValueAsString(updatedRestaurant);
        doPut(restaurant, ADMIN_REST_URL_TEST + RESTAURANT_ID_1);
        assertMatch(restaurantService.get(RESTAURANT_ID_1), updatedRestaurant);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicateDataError() throws Exception {
        doPutErr(new RestaurantTo(RESTAURANT_ID_1, RESTAURANT_2.getName(), RESTAURANT_2.getAddress()), status().isConflict());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateInvalidDataError() throws Exception {
        doPutErr(new RestaurantTo(RESTAURANT_ID_1, "U", "A"), status().isUnprocessableEntity());
    }

    private void doPutErr(RestaurantTo updatedRestaurant, ResultMatcher status) throws Exception {
        final String restaurant = objectMapper.writeValueAsString(updatedRestaurant);
        doPutErr(restaurant, ADMIN_REST_URL_TEST + RESTAURANT_ID_1, status);
        assertMatch(restaurantService.get(RESTAURANT_ID_1), RESTAURANT_1);
    }
}