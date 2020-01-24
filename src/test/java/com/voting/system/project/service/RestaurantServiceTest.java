package com.voting.system.project.service;

import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.TestMatcherUtil.assertMatch;

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
        assertMatch(restaurantService.getAll(), RESTAURANTS);
    }

    @Test
    void getAllWithMenusOnCurrentDate() {
        final List<Restaurant> actual = restaurantService.getAllWithMenusOnCurrentDate();
        assertMatch(actual, RESTAURANTS_WITH_MENU_ON_CURRENT_DATE);
        List<Menu> menus = actual.stream()
                .map(Restaurant::getMenus)
                .flatMap(Set::stream)
                .sorted(Comparator.comparing(Menu::getId))
                .collect(Collectors.toList());
        assertMatch(menus, MENUS_NOW);
    }

    @Test
    void create() {
        Restaurant saved = restaurantService.create(getNewRestaurant());
        Restaurant expected = getNewRestaurant();
        expected.setId(saved.getId());
        assertMatch(saved, expected);
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
    void createWithMenuAndDishes() {
        Restaurant saved = restaurantService.createWithMenuAndDishes(getNewRestaurantWithMenuAndDishes());
        Restaurant expected = getNewRestaurantWithMenuAndDishes();
        expected.setId(saved.getId());
        int menuId = saved.getMenus().iterator().next().getId();
        expected.getMenus().iterator().next().setId(menuId);
        assertMatch(saved, expected);
        assertMatch(saved.getMenus(), expected.getMenus());
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