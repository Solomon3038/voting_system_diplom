package com.voting.system.project.service;

import com.voting.system.project.model.Menu;
import com.voting.system.project.to.MenuWithDishesTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.voting.system.project.TestDataHelper.MENU_1;
import static com.voting.system.project.TestDataHelper.MENU_ID_1;
import static com.voting.system.project.TestDataHelper.MENU_ID_2;
import static com.voting.system.project.TestDataHelper.NOT_EXIST_ID;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_1;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_2;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_4;
import static com.voting.system.project.TestDataHelper.getNewMenu;
import static com.voting.system.project.TestDataHelper.getNewMenuWithDishes;
import static com.voting.system.project.TestDataToHelper.getUpdatedMenuTo;
import static com.voting.system.project.util.MenuTestUtil.checkAllWithDishes;
import static com.voting.system.project.util.MenuTestUtil.checkSave;
import static com.voting.system.project.util.MenuTestUtil.checkSaveWithDishes;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService menuService;

    @Test
    void get() {
        final Menu actual = mapper.map(menuService.get(MENU_ID_1, RESTAURANT_ID_1), Menu.class);
        assertMatch(actual, MENU_1);
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistException.class, () -> menuService.get(NOT_EXIST_ID, RESTAURANT_ID_1));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotExistException.class, () -> menuService.get(MENU_ID_2, RESTAURANT_ID_1));
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
        assertTrue(tos.isEmpty());
    }

    @Test
    void create() {
        final Menu saved = menuService.create(getNewMenu(), RESTAURANT_ID_4);
        checkSave(saved);
    }

    @Test
    void createWithDishes() {
        final Menu saved = menuService.create(getNewMenuWithDishes(), RESTAURANT_ID_4);
        checkSaveWithDishes(saved);
    }

    @Test
    void createNullError() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> menuService.create(null, RESTAURANT_ID_4));
        assertEquals("menu must not be null", exception.getMessage());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void update() {
        menuService.update(getUpdatedMenuTo(MENU_1), MENU_ID_1, RESTAURANT_ID_1);
        assertMatch(menuService.get(MENU_ID_1, RESTAURANT_ID_1), getUpdatedMenuTo(MENU_1));
    }

    @Test
    void updateNullError() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> menuService.update(null, MENU_ID_1, RESTAURANT_ID_1));
        assertEquals("menu must not be null", exception.getMessage());
    }

    @Test
    void updateNotExistError() {
        assertThrows(NotExistException.class,
                () -> menuService.update(getUpdatedMenuTo(MENU_1), MENU_ID_1, NOT_EXIST_ID));
    }

    @Test
    void updateNotOwnError() {
        assertThrows(NotExistException.class,
                () -> menuService.update(getUpdatedMenuTo(MENU_1), MENU_ID_1, RESTAURANT_ID_2));
    }
}