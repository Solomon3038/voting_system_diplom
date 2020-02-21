package com.voting.system.project.service;

import com.voting.system.project.model.Menu;
import com.voting.system.project.to.MenuWithDishesTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.TestDataTo.getUpdatedMenuTo;
import static com.voting.system.project.util.MenuTestUtil.*;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;

class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService menuService;

    @Test
    void get() {
        Menu actual = mapper.map(menuService.get(MENU_ID_1, RESTAURANT_ID_1), Menu.class);
        assertMatch(actual, MENU_1);
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
    void getAll() {
        final List<MenuWithDishesTo> tos = menuService.getAll(RESTAURANT_ID_1);
        final List<Menu> actual = Arrays.asList(mapper.map(tos, Menu[].class));
        checkAllWithDishes(actual);
    }

    @Test
    void getAllNotExist() {
        final List<MenuWithDishesTo> tos = menuService.getAll(NOT_EXIST_ID);
        Assertions.assertTrue(tos.isEmpty());
    }

    @Test
    void create() {
        final Menu saved = menuService.create(getNewMenu(), RESTAURANT_ID_4);
        checkSave(saved);
    }

    @Test
    void createWithDishes() {
        Menu saved = menuService.create(getNewMenuWithDishes(), RESTAURANT_ID_4);
        checkSaveWithDishes(saved);
    }

    @Test
    void createNullError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> menuService.create(null, RESTAURANT_ID_4));
        Assertions.assertEquals("menu must not be null", exception.getMessage());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void update() {
        menuService.update(getUpdatedMenuTo(MENU_1), MENU_ID_1, RESTAURANT_ID_1);
        assertMatch(menuService.get(MENU_ID_1, RESTAURANT_ID_1), getUpdatedMenuTo(MENU_1));
    }

    @Test
    void updateNullError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> menuService.update(null, MENU_ID_1, RESTAURANT_ID_1));
        Assertions.assertEquals("menu must not be null", exception.getMessage());
    }

    @Test
    void updateNotExistError() {
        Assertions.assertThrows(NotExistException.class,
                () -> menuService.update(getUpdatedMenuTo(MENU_1), MENU_ID_1, NOT_EXIST_ID));
    }

    @Test
    void updateNotOwnError() {
        Assertions.assertThrows(NotExistException.class,
                () -> menuService.update(getUpdatedMenuTo(MENU_1), MENU_ID_1, RESTAURANT_ID_2));
    }
}