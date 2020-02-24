package com.voting.system.project.repository;

import com.voting.system.project.model.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.MenuTestUtil.checkSave;
import static com.voting.system.project.util.MenuTestUtil.checkSaveWithDishes;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;

class MenuRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void findByIdAndRestaurantId() {
        Menu actual = menuRepository.findByIdAndRestaurantId(MENU_ID_1, RESTAURANT_ID_1);
        checkEntityFieldLoadingType(Menu.class, "dishes", false);
        checkEntityFieldLoadingType(Menu.class, "restaurant", true);
        assertMatch(actual, MENU_1);
    }

    @Test
    void findByIdAndRestaurantIdNotExist() {
        Menu menuNotExist = menuRepository.findByIdAndRestaurantId(NOT_EXIST_ID, RESTAURANT_ID_1);
        Assertions.assertNull(menuNotExist);
    }

    @Test
    void findByIdAndRestaurantIdNotOwn() {
        Menu menuNotExist = menuRepository.findByIdAndRestaurantId(MENU_ID_2, RESTAURANT_ID_1);
        Assertions.assertNull(menuNotExist);
    }

    @Test
    void findAllByRestaurantIdOrderByRegisteredDesc() {
        List<Menu> actual = menuRepository.findAllByRestaurantIdOrderByRegisteredDesc(RESTAURANT_ID_1);
        checkEntityFieldLoadingType(Menu.class, "dishes", false);
        assertMatch(actual, MENU_1, MENU_1_NOW);
    }

    @Test
    void saveWithDishes() {
        final Menu menu = getNewMenuWithDishes();
        menu.setRestaurant(RESTAURANT_4_NO_MENU);
        Menu saved = menuRepository.save(menu);
        checkSaveWithDishes(saved);
    }

    @Test
    void save() {
        final Menu menu = getNewMenu();
        menu.setRestaurant(RESTAURANT_4_NO_MENU);
        Menu saved = menuRepository.save(menu);
        checkSave(saved);
    }

    @Test
    void update() {
        final Menu menu = getUpdatedMenu(MENU_1);
        Menu updated = menuRepository.save(menu);
        assertMatch(updated, getUpdatedMenu(MENU_1));
    }
}