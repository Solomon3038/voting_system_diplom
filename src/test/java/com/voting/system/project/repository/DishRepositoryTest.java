package com.voting.system.project.repository;

import com.voting.system.project.model.Dish;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.DishTestUtil.checkSave;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DishRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private DishRepository dishRepository;

    @Test
    void findDishByIdAndMenuId() {
        Dish actual = dishRepository.findDishByIdAndMenuId(DISH_ID_1, MENU_ID_1);
        assertMatch(actual, DISH_1_1);
        checkEntityFieldLoadingType(Dish.class, "menu", true);
    }

    @Test
    void findDishByIdAndMenuIdNotExist() {
        Dish actual = dishRepository.findDishByIdAndMenuId(NOT_EXIST_ID, MENU_ID_1);
        Assertions.assertNull(actual);
    }

    @Test
    void findDishByIdAndMenuIdNotOwn() {
        Dish actual = dishRepository.findDishByIdAndMenuId(DISH_ID_1, MENU_ID_2);
        Assertions.assertNull(actual);
    }

    @Test
    void findAllByMenuIdOrderByNameAsc() {
        List<Dish> actual = dishRepository.findAllByMenuIdOrderByNameAsc(MENU_ID_1);
        assertMatch(actual, DISH_1_3, DISH_1_2, DISH_1_1);
        checkEntityFieldLoadingType(Dish.class, "menu", true);
    }

    @Test
    void save() {
        Dish saved = getNewDish();
        saved.setMenu(MENU_1);
        Dish actual = dishRepository.save(saved);
        checkSave(actual);
    }

    @Test
    void update() {
        final Dish expected = getUpdatedDish(DISH_1_1);
        int rows = dishRepository.setValue(DISH_ID_1, MENU_ID_1, expected.getName(), expected.getPrice());
        assertEquals(1, rows);
        Dish actual = dishRepository.findDishByIdAndMenuId(DISH_ID_1, MENU_ID_1);
        assertMatch(actual, expected);
    }

    @Test
    void updateNotExist() {
        int rows = dishRepository.setValue(NOT_EXIST_ID, MENU_ID_1, "not exist", 100);
        assertEquals(0, rows);
        Dish actual = dishRepository.findDishByIdAndMenuId(NOT_EXIST_ID, MENU_ID_1);
        Assertions.assertNull(actual);
    }

    @Test
    void updateNotOwn() {
        final Dish expected = getUpdatedDish(DISH_1_1);
        int rows = dishRepository.setValue(DISH_ID_1, MENU_ID_2, expected.getName(), expected.getPrice());
        assertEquals(0, rows);
        Dish actual = dishRepository.findDishByIdAndMenuId(DISH_ID_1, MENU_ID_1);
        assertMatch(actual, DISH_1_1);
    }

    @Test
    void deleteByIdAndMenuId() {
        int result = dishRepository.deleteByIdAndMenuId(DISH_ID_1, MENU_ID_1);
        Assertions.assertEquals(1, result);
    }

    @Test
    void deleteByIdAndMenuIdNotOwn() {
        final int result = dishRepository.deleteByIdAndMenuId(DISH_ID_1, MENU_ID_2);
        Assertions.assertEquals(0, result);
    }

    @Test
    void deleteByIdAndMenuIdNotExist() {
        final int result = dishRepository.deleteByIdAndMenuId(NOT_EXIST_ID, MENU_ID_2);
        Assertions.assertEquals(0, result);
    }
}