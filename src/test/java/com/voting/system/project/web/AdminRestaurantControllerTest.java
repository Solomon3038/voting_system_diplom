package com.voting.system.project.web;

import com.voting.system.project.model.Restaurant;
import com.voting.system.project.service.DishService;
import com.voting.system.project.service.MenuService;
import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.to.RestaurantTo;
import com.voting.system.project.to.RestaurantWithMenusTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.voting.system.project.TestDataHelper.ADMIN_1_EMAIL;
import static com.voting.system.project.TestDataHelper.DISH_ID_NEXT;
import static com.voting.system.project.TestDataHelper.MENU_ID_NEXT;
import static com.voting.system.project.TestDataHelper.NOT_EXIST_ID;
import static com.voting.system.project.TestDataHelper.RESTAURANTS;
import static com.voting.system.project.TestDataHelper.RESTAURANT_1;
import static com.voting.system.project.TestDataHelper.RESTAURANT_2;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_1;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_NEXT;
import static com.voting.system.project.TestDataToHelper.getInvalidNewRestaurantWithMenuAndDishesTo;
import static com.voting.system.project.TestDataToHelper.getNewRestaurantTo;
import static com.voting.system.project.TestDataToHelper.getNewRestaurantWithMenuAndDishesTo;
import static com.voting.system.project.TestDataToHelper.getNewRestaurantWithMenuTo;
import static com.voting.system.project.TestDataToHelper.getUpdatedRestaurantTo;
import static com.voting.system.project.util.RestaurantTestUtil.checkSave;
import static com.voting.system.project.util.RestaurantTestUtil.checkSaveWithMenu;
import static com.voting.system.project.util.RestaurantTestUtil.checkSaveWithMenuAndDishes;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static com.voting.system.project.web.AdminRestaurantController.ADMIN_REST_URL;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void createNotNew() throws Exception {
        final String restaurant = objectMapper.writeValueAsString(new Restaurant(RESTAURANT_ID_NEXT, "New Name", "New Address"));
        doPostErr(restaurant, ADMIN_REST_URL_TEST, status().isUnprocessableEntity());
        assertThrows(NotExistException.class, () -> restaurantService.get(RESTAURANT_ID_NEXT));
    }

    @Test
    void createDuplicateData() throws Exception {
        final String restaurant = objectMapper.writeValueAsString(new Restaurant(null, RESTAURANT_1.getName(), RESTAURANT_1.getAddress()));
        doPostErr(restaurant, ADMIN_REST_URL_TEST, status().isConflict());
        assertThrows(NotExistException.class, () -> restaurantService.get(RESTAURANT_ID_NEXT));
    }

    @Test
    void createInvalidData() throws Exception {
        final String restaurant = objectMapper.writeValueAsString(getInvalidNewRestaurantWithMenuAndDishesTo());
        doPostErr(restaurant, ADMIN_REST_URL_TEST, status().isUnprocessableEntity());
        assertThrows(NotExistException.class, () -> restaurantService.get(RESTAURANT_ID_NEXT));
        assertThrows(NotExistException.class, () -> menuService.get(MENU_ID_NEXT, RESTAURANT_ID_NEXT));
        assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_NEXT, MENU_ID_NEXT));
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
    void updateNotExist() throws Exception {
        doPutErr(new RestaurantTo(NOT_EXIST_ID, "Updated Name", "Updated Address"), status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicateData() throws Exception {
        doPutErr(new RestaurantTo(RESTAURANT_ID_1, RESTAURANT_2.getName(), RESTAURANT_2.getAddress()), status().isConflict());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateInvalidData() throws Exception {
        doPutErr(new RestaurantTo(RESTAURANT_ID_1, "U", "A"), status().isUnprocessableEntity());
    }

    private void doPutErr(RestaurantTo updatedRestaurant, ResultMatcher status) throws Exception {
        final String restaurant = objectMapper.writeValueAsString(updatedRestaurant);
        doPutErr(restaurant, ADMIN_REST_URL_TEST + RESTAURANT_ID_1, status);
        assertMatch(restaurantService.get(RESTAURANT_ID_1), RESTAURANT_1);
    }
}