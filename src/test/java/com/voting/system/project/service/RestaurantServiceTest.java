package com.voting.system.project.service;

import com.voting.system.project.AbstractTest;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.to.MenuItemDishNameTo;
import com.voting.system.project.to.RestaurantTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.voting.system.project.TestDataHelper.NOT_EXIST_ID;
import static com.voting.system.project.TestDataHelper.RESTAURANTS;
import static com.voting.system.project.TestDataHelper.RESTAURANTS_WITH_MENU_ON_CURRENT_DATE;
import static com.voting.system.project.TestDataHelper.RESTAURANT_1;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_1;
import static com.voting.system.project.TestDataHelper.getNewRestaurant;
import static com.voting.system.project.TestDataHelper.getUpdatedRestaurant;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantServiceTest extends AbstractTest {

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
        assertMatch(restaurantService.getAll(), RESTAURANTS);
    }

    @Test
    void getAllWithMenusOnCurrentDate() {
        final List<RestaurantTo> actual = restaurantService.getAllWithMenusOnDate(LocalDate.now());
        final List<RestaurantTo> tos = mapper.mapAsList(RESTAURANTS_WITH_MENU_ON_CURRENT_DATE, RestaurantTo.class);
        assertMatch(actual, tos);
        for (int i = 0; i < actual.size(); i++) {
            List<MenuItemDishNameTo> menus = actual.get(i).getMenuItemDishNameTos().stream()
                    .sorted(Comparator.comparing(MenuItemDishNameTo::getDishName))
                    .collect(Collectors.toList());
            assertMatch(menus, tos.get(i).getMenuItemDishNameTos());
        }
    }

    @Test
    void create() {
        final Restaurant saved = restaurantService.create(getNewRestaurant());
        final Restaurant expected = getNewRestaurant();
        expected.setId(saved.getId());
        assertMatch(saved, expected);
    }

    @Test
    void createNullError() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> restaurantService.create(null));
        assertEquals("restaurant must not be null", exception.getMessage());
    }

    @Test
    void update() {
        restaurantService.update(getUpdatedRestaurant(RESTAURANT_1), RESTAURANT_ID_1);
        assertMatch(restaurantService.get(RESTAURANT_ID_1), getUpdatedRestaurant(RESTAURANT_1));
    }

    @Test
    void updateNullError() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> restaurantService.update(null, RESTAURANT_ID_1));
        assertEquals("restaurant must not be null", exception.getMessage());
    }

    @Test
    void updateNotExist() {
        final Restaurant created = getNewRestaurant();
        created.setId(NOT_EXIST_ID);
        assertThrows(NotExistException.class, () -> restaurantService.update(created, RESTAURANT_ID_1));
    }
}