package com.voting.system.project.service;

import com.voting.system.project.AbstractTest;
import com.voting.system.project.model.Dish;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voting.system.project.TestDataHelper.DISH_1;
import static com.voting.system.project.TestDataHelper.DISH_ID_1;
import static com.voting.system.project.TestDataHelper.NOT_EXIST_ID;
import static com.voting.system.project.TestDataHelper.RESTAURANT_1_DISHES;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_1;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_2;
import static com.voting.system.project.TestDataHelper.getNewDish;
import static com.voting.system.project.TestDataHelper.getUpdatedDish;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DishServiceTest extends AbstractTest {

    @Autowired
    private DishService dishService;

    @Test
    void getAll() {
        assertMatch(dishService.getAll(RESTAURANT_ID_1), RESTAURANT_1_DISHES);
    }

    @Test
    void getAllNotExist() {
        assertTrue(dishService.getAll(NOT_EXIST_ID).isEmpty());
    }

    @Test
    void get() {
        assertMatch(dishService.get(DISH_ID_1, RESTAURANT_ID_1), DISH_1);
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistException.class, () -> dishService.get(NOT_EXIST_ID, RESTAURANT_ID_1));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_1, RESTAURANT_ID_2));
    }

    @Test
    void save() {
        final Dish actual = dishService.create(getNewDish(), RESTAURANT_ID_1);
        final Dish expected = getNewDish();
        expected.setId(actual.getId());
        assertMatch(actual, expected);
    }

    @Test
    void saveNullError() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> dishService.create(null, RESTAURANT_ID_1));
        assertEquals("dish must not be null", exception.getMessage());
    }

    @Test
    void update() {
        dishService.update(getUpdatedDish(DISH_1), DISH_ID_1, RESTAURANT_ID_1);
        final Dish actual = dishService.get(DISH_ID_1, RESTAURANT_ID_1);
        assertMatch(actual, getUpdatedDish(DISH_1));
    }

    @Test
    void updateNullError() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> dishService.update(null, DISH_ID_1, RESTAURANT_ID_1));
        assertEquals("dish must not be null", exception.getMessage());
    }

    @Test
    void updateNotOwn() {
        assertThrows(NotExistException.class, () -> dishService.update(getUpdatedDish(DISH_1), DISH_ID_1, RESTAURANT_ID_2));
    }

    @Test
    void updateNotExist() {
        final Dish updatedDish = getUpdatedDish(DISH_1);
        updatedDish.setId(NOT_EXIST_ID);
        assertThrows(NotExistException.class, () -> dishService.update(updatedDish, NOT_EXIST_ID, RESTAURANT_ID_1));
    }
}