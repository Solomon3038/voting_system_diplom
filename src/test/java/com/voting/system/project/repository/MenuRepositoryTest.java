package com.voting.system.project.repository;

import com.voting.system.project.model.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.MenuTestUtil.checkSave;
import static com.voting.system.project.util.MenuTestUtil.checkSaveWithDishes;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertMatch(actual, MENU_1_NOW, MENU_1);
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
        int rows = menuRepository.setValue(MENU_ID_1, RESTAURANT_ID_1, menu.getRegistered());
        assertEquals(1, rows);
        Menu updated = menuRepository.findByIdAndRestaurantId(MENU_ID_1, RESTAURANT_ID_1);
        assertMatch(updated, getUpdatedMenu(MENU_1));
    }

    @Test
    void updateNotExist() {
        int rows = menuRepository.setValue(NOT_EXIST_ID, RESTAURANT_ID_1, LocalDate.of(2021, 3, 5));
        assertEquals(0, rows);
        Menu updated = menuRepository.findByIdAndRestaurantId(NOT_EXIST_ID, RESTAURANT_ID_1);
        Assertions.assertNull(updated);
    }

    @Test
    void updateNotOwn() {
        final Menu menu = getUpdatedMenu(MENU_1);
        int rows = menuRepository.setValue(MENU_ID_1, RESTAURANT_ID_2, menu.getRegistered());
        assertEquals(0, rows);
        Menu updated = menuRepository.findByIdAndRestaurantId(MENU_ID_1, RESTAURANT_ID_1);
        assertMatch(updated, MENU_1);
    }
}