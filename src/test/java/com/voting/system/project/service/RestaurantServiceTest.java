package com.voting.system.project.service;

import com.voting.system.project.model.Restaurant;
import com.voting.system.project.to.RestaurantTo;
import com.voting.system.project.to.RestaurantWithMenusTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.voting.system.project.TestDataHelper.NOT_EXIST_ID;
import static com.voting.system.project.TestDataHelper.RESTAURANTS;
import static com.voting.system.project.TestDataHelper.RESTAURANTS_WITH_MENU_ON_CURRENT_DATE;
import static com.voting.system.project.TestDataHelper.RESTAURANT_1;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_1;
import static com.voting.system.project.TestDataHelper.getNewRestaurant;
import static com.voting.system.project.TestDataHelper.getNewRestaurantWithMenu;
import static com.voting.system.project.TestDataHelper.getNewRestaurantWithMenuAndDishes;
import static com.voting.system.project.TestDataToHelper.getNewRestaurantTo;
import static com.voting.system.project.TestDataToHelper.getUpdatedRestaurantTo;
import static com.voting.system.project.util.RestaurantTestUtil.checkAllWithMenusOnCurrentDate;
import static com.voting.system.project.util.RestaurantTestUtil.checkSave;
import static com.voting.system.project.util.RestaurantTestUtil.checkSaveWithMenu;
import static com.voting.system.project.util.RestaurantTestUtil.checkSaveWithMenuAndDishes;
import static com.voting.system.project.util.RestaurantTestUtil.checkUpdate;
import static com.voting.system.project.util.RestaurantUtil.getFromTo;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void get() {
        assertMatch(restaurantService.get(RESTAURANT_ID_1), RESTAURANT_1);
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistException.class, () -> restaurantService.get(NOT_EXIST_ID));
    }

    @Test
    void getAll() {
        final List<RestaurantTo> actual = restaurantService.getAll();
        assertMatch(Arrays.asList(mapper.map(actual, Restaurant[].class)), RESTAURANTS);
    }

    @Test
    void getAllWithMenusOnCurrentDate() {
        final List<RestaurantWithMenusTo> actual = restaurantService.getAllWithMenusOnCurrentDate();
        final List<Restaurant> actualList = Arrays.asList(mapper.map(actual, Restaurant[].class));
        assertMatch(actualList, RESTAURANTS_WITH_MENU_ON_CURRENT_DATE);
        checkAllWithMenusOnCurrentDate(actualList);
    }

    @Test
    void create() {
        final Restaurant saved = restaurantService.create(getNewRestaurant());
        checkSave(saved);
    }

    @Test
    void createNullError() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> restaurantService.create(null));
        assertEquals("restaurant must not be null", exception.getMessage());
    }

    @Test
    void createWithMenu() {
        final Restaurant saved = restaurantService.create(getNewRestaurantWithMenu());
        checkSaveWithMenu(saved);
    }

    @Test
    void createWithMenuAndDishes() {
        final Restaurant saved = restaurantService.create(getNewRestaurantWithMenuAndDishes());
        checkSaveWithMenuAndDishes(saved);
    }

    @Test
    void createWithMenuAndDishesNullError() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> restaurantService.create(null));
        assertEquals("restaurant must not be null", exception.getMessage());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void update() {
        restaurantService.update(getUpdatedRestaurantTo(RESTAURANT_1), RESTAURANT_ID_1);
        final Restaurant updated = getFromTo(restaurantService.get(RESTAURANT_ID_1));
        checkUpdate(updated);
    }

    @Test
    void updateNullError() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> restaurantService.update(null, RESTAURANT_ID_1));
        assertEquals("restaurant must not be null", exception.getMessage());
    }

    @Test
    void updateNotExist() {
        final RestaurantTo created = getNewRestaurantTo();
        created.setId(NOT_EXIST_ID);
        assertThrows(NotExistException.class, () -> restaurantService.update(created, RESTAURANT_ID_1));
    }
}