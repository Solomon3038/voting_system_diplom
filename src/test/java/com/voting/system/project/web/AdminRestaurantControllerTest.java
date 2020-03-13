package com.voting.system.project.web;

import com.voting.system.project.model.Restaurant;
import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.voting.system.project.TestDataHelper.ADMIN_1_EMAIL;
import static com.voting.system.project.TestDataHelper.NOT_EXIST_ID;
import static com.voting.system.project.TestDataHelper.RESTAURANTS;
import static com.voting.system.project.TestDataHelper.RESTAURANT_1;
import static com.voting.system.project.TestDataHelper.RESTAURANT_2;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_1;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_NEXT;
import static com.voting.system.project.TestDataHelper.getNewRestaurant;
import static com.voting.system.project.TestDataHelper.getUpdatedRestaurant;
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

    @Test
    void getAll() throws Exception {
        final String restaurants = objectMapper.writeValueAsString(RESTAURANTS);
        doGet(ADMIN_REST_URL_TEST, restaurants);
    }

    @Test
    void get() throws Exception {
        final String restaurant = objectMapper.writeValueAsString(RESTAURANT_1);
        doGet(ADMIN_REST_URL_TEST + RESTAURANT_ID_1, restaurant);
    }

    @Test
    void getNotExist() throws Exception {
        doGetNotExist(ADMIN_REST_URL_TEST + NOT_EXIST_ID);
    }

    @Test
    void createWithLocation() throws Exception {
        final Restaurant newRestaurant = getNewRestaurant();
        final String restaurant = objectMapper.writeValueAsString(newRestaurant);
        final String result = doPost(restaurant, ADMIN_REST_URL_TEST);
        final Restaurant created = objectMapper.readValue(result, Restaurant.class);
        final Integer id = created.getId();
        newRestaurant.setId(id);
        assertMatch(created, newRestaurant);
        assertMatch(restaurantService.get(id), newRestaurant);
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
        final String restaurant = objectMapper.writeValueAsString(new Restaurant(RESTAURANT_ID_NEXT, "N", ""));
        doPostErr(restaurant, ADMIN_REST_URL_TEST, status().isUnprocessableEntity());
        assertThrows(NotExistException.class, () -> restaurantService.get(RESTAURANT_ID_NEXT));
    }

    @Test
    void update() throws Exception {
        final Restaurant updated = getUpdatedRestaurant(RESTAURANT_1);
        final String restaurant = objectMapper.writeValueAsString(updated);
        doPut(restaurant, ADMIN_REST_URL_TEST + RESTAURANT_ID_1);
        assertMatch(restaurantService.get(RESTAURANT_ID_1), updated);
    }

    @Test
    void updateNotExist() throws Exception {
        doPutErr(new Restaurant(NOT_EXIST_ID, "Updated Name", "Updated Address"), status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicateData() throws Exception {
        doPutErr(new Restaurant(RESTAURANT_ID_1, RESTAURANT_2.getName(), RESTAURANT_2.getAddress()), status().isConflict());
    }

    @Test
    void updateInvalidData() throws Exception {
        doPutErr(new Restaurant(RESTAURANT_ID_1, "U", ""), status().isUnprocessableEntity());
    }

    private void doPutErr(Restaurant updatedRestaurant, ResultMatcher status) throws Exception {
        final String restaurant = objectMapper.writeValueAsString(updatedRestaurant);
        doPutErr(restaurant, ADMIN_REST_URL_TEST + RESTAURANT_ID_1, status);
        assertMatch(restaurantService.get(RESTAURANT_ID_1), RESTAURANT_1);
    }
}