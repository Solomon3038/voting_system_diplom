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
    }

    @Test
    void createWithNotEmptyMenusError() {
    }

    @Test
    void createWithMenuAndDishes() {
    }

    @Test
    void createWithMenuAndDishesNullError() {
    }

    @Test
    void createWithMenuAndDishesNullMenusError() {
    }

    @Test
    void createWithMenuAndDishesNotOneMenuError() {
    }

    @Test
    void createWithMenuAndDishesNullDishesError() {
    }

    @Test
    void createWithMenuAndDishesEmptyDishesError() {
    }
}