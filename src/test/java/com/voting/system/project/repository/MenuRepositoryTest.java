package com.voting.system.project.repository;

import com.voting.system.project.model.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.TestMatcherUtil.assertMatch;
import static com.voting.system.project.util.MenuTestUtil.*;

class MenuRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void findByIdWithDishes() {
        Menu actual = menuRepository.findByIdWithDishes(MENU_ID_1, RESTAURANT_ID_1);
        checkEntityFieldLoadingType(Menu.class, "dishes", false);
        checkWithDishes(actual);
    }

    @Test
    void findByIdWithDishesNotExist() {
        Menu menuNotExist = menuRepository.findByIdWithDishes(NOT_EXIST_ID, RESTAURANT_ID_1);
        Assertions.assertNull(menuNotExist);
    }

    @Test
    void findByIdWithDishesNotOwn() {
        Menu menuNotExist = menuRepository.findByIdWithDishes(MENU_ID_2, RESTAURANT_ID_1);
        Assertions.assertNull(menuNotExist);
    }

    @Test
    void findAll() {
        List<Menu> actual = menuRepository.findAllByRestaurantId(RESTAURANT_ID_1);
        assertMatch(actual, RESTAURANT_1_MENUS);
        checkEntityFieldLoadingType(Menu.class, "dishes", true);
    }

    @Test
    void findAllWithDishes() {
        List<Menu> actual = menuRepository.findAllByRestaurantIdWithDishes(RESTAURANT_ID_1);
        checkEntityFieldLoadingType(Menu.class, "dishes", false);
        checkAllWithDishes(actual);
    }

    @Test
    void saveWithDishes() {
        Menu saved = menuRepository.save(getNewMenuWithDishes());
        checkSaveWithDishes(saved);
    }
}