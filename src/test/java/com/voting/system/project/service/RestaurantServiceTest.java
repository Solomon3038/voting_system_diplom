package com.voting.system.project.service;

import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.to.RestaurantTo;
import com.voting.system.project.to.RestaurantWithMenusTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.RestaurantTestUtil.*;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void get() {
        assertMatch(restaurantService.get(RESTAURANT_ID_1), RESTAURANT_1);
    }

    @Test
    void getNotExist() {
        Assertions.assertThrows(NotExistException.class, () -> restaurantService.get(NOT_EXIST_ID));
    }

    @Test
    void getAll() {
        final List<RestaurantTo> actual = restaurantService.getAll();
        assertMatch(Arrays.asList(mapper.map(actual, Restaurant[].class)), RESTAURANTS);
    }

    @Test
    void getAllWithMenusOnCurrentDate() {
        final List<RestaurantWithMenusTo> actual = restaurantService.getAllWithMenusOnCurrentDate();
        List<Restaurant> actualList = Arrays.asList(mapper.map(actual, Restaurant[].class));
        assertMatch(actualList, RESTAURANTS_WITH_MENU_ON_CURRENT_DATE);
        checkAllWithMenusOnCurrentDate(actualList);
    }

    @Test
    void create() {
        Restaurant saved = restaurantService.create(getNewRestaurant());
        checkSave(saved);
    }

    @Test
    void update() {
        restaurantService.update(getUpdatedRestaurant(RESTAURANT_1));
        Restaurant updated = mapper.map(restaurantService.get(RESTAURANT_ID_1), Restaurant.class);
        checkUpdate(updated);
    }

    @Test
    void createNullError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> restaurantService.create(null));
        Assertions.assertEquals("restaurant must not be null", exception.getMessage());
    }

    @Test
    void createWithNotEmptyMenusError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> restaurantService.create(getNewRestaurantWithMenuAndDishes()));
        Assertions.assertEquals("list of menus must be empty", exception.getMessage());
    }

    @Test
    void updateNullError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> restaurantService.update(null));
        Assertions.assertEquals("restaurant must not be null", exception.getMessage());
    }

    @Test
    void updateNotExist() {
        Restaurant created = getNewRestaurant();
        created.setId(NOT_EXIST_ID);
        Assertions.assertThrows(NotExistException.class, () -> restaurantService.update(created));
    }

    @Test
    void updateWithNotEmptyMenusError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> restaurantService.update(getNewRestaurantWithMenuAndDishes()));
        Assertions.assertEquals("list of menus must be empty", exception.getMessage());
    }

    @Test
    void createWithMenuAndDishes() {
        Restaurant saved = restaurantService.createWithMenuAndDishes(getNewRestaurantWithMenuAndDishes());
        checkSaveWithMenusAndDishes(saved);
    }

    @Test
    void createWithMenuAndDishesNullError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> restaurantService.createWithMenuAndDishes(null));
        Assertions.assertEquals("restaurant must not be null", exception.getMessage());
    }

    @Test
    void createWithMenuAndDishesEmptyMenusError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> restaurantService.createWithMenuAndDishes(getNewRestaurant()));
        Assertions.assertEquals("restaurant must have one menu", exception.getMessage());
    }

    @Test
    void createWithMenuAndDishesNotOneMenuError() {
        Restaurant restaurant = getNewRestaurantWithMenuAndDishes();
        restaurant.setMenu(new Menu(null, restaurant));
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> restaurantService.createWithMenuAndDishes(restaurant));
        Assertions.assertEquals("restaurant must have one menu", exception.getMessage());
    }

    @Test
    void createWithMenuAndDishesEmptyDishesError() {
        Restaurant restaurant = getNewRestaurantWithMenuAndDishes();
        final Menu menu = restaurant.getMenus().iterator().next();
        menu.setDishes(null);
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> restaurantService.createWithMenuAndDishes(restaurant));
        Assertions.assertEquals("dishes must not be empty", exception.getMessage());
    }
}