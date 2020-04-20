package com.voting.system.project.service;

import com.voting.system.project.AbstractTest;
import com.voting.system.project.to.MenuItemDishIdTo;
import com.voting.system.project.to.MenuItemDishNameTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.system.project.TestDataHelper.MENU_1_1;
import static com.voting.system.project.TestDataHelper.MENU_ID_1;
import static com.voting.system.project.TestDataHelper.MENU_ID_4;
import static com.voting.system.project.TestDataHelper.NOT_EXIST_ID;
import static com.voting.system.project.TestDataHelper.RESTAURANT_1_MENUS;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_1;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_2;
import static com.voting.system.project.TestDataHelper.getNewMenuItemDishIdTo;
import static com.voting.system.project.TestDataHelper.getUpdatedMenuItemDishIdTo;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuServiceTest extends AbstractTest {

    @Autowired
    private MenuItemService menuService;

    @Test
    void getAll() {
        final List<MenuItemDishNameTo> actual = menuService.getAll(RESTAURANT_ID_1);
        final List<MenuItemDishNameTo> expected = mapper.mapAsList(RESTAURANT_1_MENUS, MenuItemDishNameTo.class);
        assertMatch(actual, expected);
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(actual.get(i).getDishName(), expected.get(i).getDishName());
        }
    }

    @Test
    void getAllNotExist() {
        assertTrue(menuService.getAll(NOT_EXIST_ID).isEmpty());
    }

    @Test
    void get() {
        assertMatch(menuService.get(MENU_ID_1, RESTAURANT_ID_1), MENU_1_1);
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistException.class, () -> menuService.get(NOT_EXIST_ID, RESTAURANT_ID_1));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotExistException.class, () -> menuService.get(MENU_ID_4, RESTAURANT_ID_1));
    }

    @Test
    void create() {
        final MenuItemDishNameTo saved = menuService.create(getNewMenuItemDishIdTo());
        final MenuItemDishIdTo expected = getNewMenuItemDishIdTo();
        expected.setId(saved.getId());
        assertMatch(saved, expected);
    }

    @Test
    void createNullError() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> menuService.create(null));
        assertEquals("menuItemDishIdTo must not be null", exception.getMessage());
    }

    @Test
    void update() {
        menuService.update(getUpdatedMenuItemDishIdTo(MENU_1_1), MENU_ID_1, RESTAURANT_ID_1);
        assertMatch(menuService.get(MENU_ID_1, RESTAURANT_ID_1), getUpdatedMenuItemDishIdTo(MENU_1_1));
    }

    @Test
    void updateNullError() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> menuService.update(null, MENU_ID_1, RESTAURANT_ID_1));
        assertEquals("menuItemDishIdTo must not be null", exception.getMessage());
    }

    @Test
    void updateNotExistError() {
        assertThrows(NotExistException.class,
                () -> menuService.update(getUpdatedMenuItemDishIdTo(MENU_1_1), MENU_ID_1, NOT_EXIST_ID));
    }

    @Test
    void updateNotOwnError() {
        assertThrows(NotExistException.class,
                () -> menuService.update(getUpdatedMenuItemDishIdTo(MENU_1_1), MENU_ID_1, RESTAURANT_ID_2));
    }
}