package com.voting.system.project.service;

import com.voting.system.project.model.Dish;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.DishTestUtil.checkSave;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;

class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService dishService;

    @Test
    void getAll() {
        assertMatch(dishService.getAll(MENU_ID_1), DISH_1_3, DISH_1_2, DISH_1_1);
    }

    @Test
    void get() {
        assertMatch(dishService.get(DISH_ID_1, MENU_ID_1), DISH_1_1);
    }

    @Test
    void getNotExist() {
        Assertions.assertThrows(NotExistException.class, () -> dishService.get(NOT_EXIST_ID, MENU_ID_1));
    }

    @Test
    void getNotOwn() {
        Assertions.assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_1, MENU_ID_2));
    }

    @Test
    void save() {
        Dish actual = dishService.save(getNewDish(), MENU_ID_1);
        checkSave(actual);
    }

    @Test
    void saveNullError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> dishService.save(null, MENU_ID_1));
        Assertions.assertEquals("dish must not be null", exception.getMessage());
    }

    @Test
    void saveNotExist() {
        Assertions.assertThrows(NotExistException.class, () -> dishService.save(getNewDish(), NOT_EXIST_ID));
    }

    @Test
    void update() {
        dishService.update(getUpdatedDish(DISH_1_1), MENU_ID_1);
        Dish actual = dishService.get(DISH_ID_1, MENU_ID_1);
        assertMatch(actual, getUpdatedDish(DISH_1_1));
    }

    @Test
    void updateNullError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> dishService.update(null, MENU_ID_1));
        Assertions.assertEquals("dish must not be null", exception.getMessage());
    }

    @Test
    void updateNotOwn() {
        Assertions.assertThrows(NotExistException.class,
                () -> dishService.update(getUpdatedDish(DISH_1_1), MENU_ID_2));
    }

    @Test
    void updateNotExist() {
        final Dish updatedDish = getUpdatedDish(DISH_1_1);
        updatedDish.setId(NOT_EXIST_ID);
        Assertions.assertThrows(NotExistException.class, () -> {
            dishService.update(updatedDish, MENU_ID_1);
        });
    }

    @Test
    void delete() {
        dishService.delete(DISH_ID_1, MENU_ID_1);
        Assertions.assertThrows(NotExistException.class,
                () -> dishService.get(DISH_ID_1, MENU_ID_1));
    }

    @Test
    void deleteNotOwn() {
        Assertions.assertThrows(NotExistException.class,
                () -> dishService.delete(DISH_ID_1, MENU_ID_2));
    }

    @Test
    void deleteNotExist() {
        Assertions.assertThrows(NotExistException.class,
                () -> dishService.delete(NOT_EXIST_ID, MENU_ID_1));
    }
}