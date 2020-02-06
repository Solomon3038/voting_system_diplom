package com.voting.system.project.service;

import com.voting.system.project.model.Menu;
import com.voting.system.project.to.MenuTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.MenuTestUtil.*;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;

class MenuServiceTest extends AbstractServiceTest {

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
    void get() {
        Menu actual = mapper.map(menuService.get(MENU_ID_1, RESTAURANT_ID_1), Menu.class);
        checkWithDishes(actual);
    }

    @Test
    void getNotExist() {
        Assertions.assertThrows(NotExistException.class, () -> menuService.get(NOT_EXIST_ID, RESTAURANT_ID_1));
    }

    @Test
    void getNotOwn() {
        Assertions.assertThrows(NotExistException.class, () -> menuService.get(MENU_ID_2, RESTAURANT_ID_1));
    }

    @Test
    void getAllWithDishes() {
        final List<Menu> actual = menuService.getAllWithDishes(RESTAURANT_ID_1);
        checkAllWithDishes(actual);
    }

    @Test
    void getAll() {
        final List<MenuTo> tos = menuService.getAll(RESTAURANT_ID_1);
        assertMatch(Arrays.asList(mapper.map(tos, Menu[].class), MENU_1_NOW, MENU_1));
    }

    @Test
    void createWithDishes() {
        Menu saved = menuService.createWithDishes(getNewMenuWithDishes(), RESTAURANT_ID_4);
        checkSaveWithDishes(saved);
    }

    @Test
    void createWithDishesNullError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> menuService.createWithDishes(null, RESTAURANT_ID_4));
        Assertions.assertEquals("menu must not be null", exception.getMessage());
    }

    @Test
    void createWithDishesNotExistError() {
        Assertions.assertThrows(NotExistException.class,
                () -> menuService.createWithDishes(getNewMenuWithDishes(), NOT_EXIST_ID));
    }

    @Test
    void saveWithDishesEmptyDishesError() {
        Menu menu = getNewMenuWithDishes();
        menu.setDishes(null);
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> menuService.createWithDishes(menu, RESTAURANT_ID_4));
        Assertions.assertEquals("dishes must not be empty", exception.getMessage());
    }

    @Test
    void create() {
        Menu saved = menuService.create(getNewMenu(), RESTAURANT_ID_4);
        checkSave(saved);
    }

    @Test
    void createNullError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> menuService.create(null, RESTAURANT_ID_4));
        Assertions.assertEquals("menu must not be null", exception.getMessage());
    }

    @Test
    void createNotExistError() {
        Assertions.assertThrows(NotExistException.class,
                () -> menuService.create(getNewMenu(), NOT_EXIST_ID));
    }

    @Test
    void update() {
        menuService.update(getUpdatedMenu(MENU_1), RESTAURANT_ID_1);
        assertMatch(menuService.get(MENU_ID_1, RESTAURANT_ID_1), getUpdatedMenu(MENU_1));
    }

    @Test
    void updateNullError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> menuService.update(null, RESTAURANT_ID_1));
        Assertions.assertEquals("menu must not be null", exception.getMessage());
    }

    @Test
    void updateNotExistError() {
        Assertions.assertThrows(NotExistException.class,
                () -> menuService.update(getUpdatedMenu(MENU_1), NOT_EXIST_ID));
    }

    @Test
    void updateNotOwnError() {
        Assertions.assertThrows(NotExistException.class,
                () -> menuService.update(getUpdatedMenu(MENU_1), RESTAURANT_ID_2));
    }
}