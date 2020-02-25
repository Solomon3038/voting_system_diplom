package com.voting.system.project.repository;

import com.voting.system.project.model.Dish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.system.project.TestDataHelper.DISH_1_1;
import static com.voting.system.project.TestDataHelper.DISH_1_2;
import static com.voting.system.project.TestDataHelper.DISH_1_3;
import static com.voting.system.project.TestDataHelper.DISH_ID_1;
import static com.voting.system.project.TestDataHelper.MENU_1;
import static com.voting.system.project.TestDataHelper.MENU_ID_1;
import static com.voting.system.project.TestDataHelper.MENU_ID_2;
import static com.voting.system.project.TestDataHelper.NOT_EXIST_ID;
import static com.voting.system.project.TestDataHelper.getNewDish;
import static com.voting.system.project.TestDataHelper.getUpdatedDish;
import static com.voting.system.project.util.DishTestUtil.checkSave;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DishRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private DishRepository dishRepository;

    @Test
    void findDishByIdAndMenuId() {
        final Dish actual = dishRepository.findDishByIdAndMenuId(DISH_ID_1, MENU_ID_1);
        assertMatch(actual, DISH_1_1);
        checkEntityFieldLoadingType(Dish.class, "menu", true);
    }

    @Test
    void findDishByIdAndMenuIdNotExist() {
        final Dish actual = dishRepository.findDishByIdAndMenuId(NOT_EXIST_ID, MENU_ID_1);
        assertNull(actual);
    }

    @Test
    void findDishByIdAndMenuIdNotOwn() {
        Dish actual = dishRepository.findDishByIdAndMenuId(DISH_ID_1, MENU_ID_2);
        assertNull(actual);
    }

    @Test
    void findAllByMenuIdOrderByNameAsc() {
        final List<Dish> actual = dishRepository.findAllByMenuIdOrderByNameAsc(MENU_ID_1);
        assertMatch(actual, DISH_1_3, DISH_1_2, DISH_1_1);
        checkEntityFieldLoadingType(Dish.class, "menu", true);
    }

    @Test
    void save() {
        final Dish saved = getNewDish();
        saved.setMenu(MENU_1);
        final Dish actual = dishRepository.save(saved);
        checkSave(actual);
    }

    @Test
    void update() {
        final Dish actual = dishRepository.save(getUpdatedDish(DISH_1_1));
        final Dish expected = getUpdatedDish(DISH_1_1);
        assertMatch(actual, expected);
    }

    @Test
    void deleteByIdAndMenuId() {
        final int result = dishRepository.deleteByIdAndMenuId(DISH_ID_1, MENU_ID_1);
        assertEquals(1, result);
    }

    @Test
    void deleteByIdAndMenuIdNotOwn() {
        final int result = dishRepository.deleteByIdAndMenuId(DISH_ID_1, MENU_ID_2);
        assertEquals(0, result);
    }

    @Test
    void deleteByIdAndMenuIdNotExist() {
        final int result = dishRepository.deleteByIdAndMenuId(NOT_EXIST_ID, MENU_ID_2);
        assertEquals(0, result);
    }
}