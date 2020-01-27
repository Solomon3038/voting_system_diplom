package com.voting.system.project.service;

import com.voting.system.project.model.Menu;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.TestMatcherUtil.assertMatch;
import static com.voting.system.project.util.MenuTestUtil.*;

class MenuServiceTest extends AbstractServiceTest{

    @Autowired
    private MenuService menuService;

    @Test
    void getWithDishes() {
        Menu actual = menuService.getWithDishes(MENU_ID_1, RESTAURANT_ID_1);
        checkWithDishes(actual);
    }

    @Test
    void getWithDishesNotExist() {
        Assertions.assertThrows(NotExistException.class, () -> menuService.getWithDishes(NOT_EXIST_ID, RESTAURANT_ID_1));
    }

    @Test
    void getWithDishesNotOwn() {
        Assertions.assertThrows(NotExistException.class, () -> menuService.getWithDishes(MENU_ID_2, RESTAURANT_ID_1));
    }

    @Test
    void getAllWithDishes() {
        final List<Menu> actual = menuService.getAllWithDishes(RESTAURANT_ID_1);
        checkAllWithDishes(actual);
    }

    @Test
    void getAll() {
        final List<Menu> actual = menuService.getAll(RESTAURANT_ID_1);
        assertMatch(actual, RESTAURANT_1_MENUS);
    }

    @Test
    void createWithDishes() {
        Menu saved = menuService.createWithDishes(getNewMenuWithDishes());
        checkSaveWithDishes(saved);
    }

    @Test
    void createWithDishesNullError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> menuService.createWithDishes(null));
        Assertions.assertEquals("menu must not be null", exception.getMessage());
    }

    @Test
    void saveWithDishesEmptyDishesError() {
        Menu menu = getNewMenuWithDishes();
        menu.setDishes(null);
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> menuService.createWithDishes(menu));
        Assertions.assertEquals("dishes must not be empty", exception.getMessage());
    }
}