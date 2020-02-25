package com.voting.system.project.repository;

import com.voting.system.project.model.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.system.project.TestDataHelper.MENU_1;
import static com.voting.system.project.TestDataHelper.MENU_1_NOW;
import static com.voting.system.project.TestDataHelper.MENU_ID_1;
import static com.voting.system.project.TestDataHelper.MENU_ID_2;
import static com.voting.system.project.TestDataHelper.NOT_EXIST_ID;
import static com.voting.system.project.TestDataHelper.RESTAURANT_4_NO_MENU;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_1;
import static com.voting.system.project.TestDataHelper.getNewMenu;
import static com.voting.system.project.TestDataHelper.getNewMenuWithDishes;
import static com.voting.system.project.TestDataHelper.getUpdatedMenu;
import static com.voting.system.project.util.MenuTestUtil.checkSave;
import static com.voting.system.project.util.MenuTestUtil.checkSaveWithDishes;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.jupiter.api.Assertions.assertNull;

class MenuRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void findByIdAndRestaurantId() {
        final Menu actual = menuRepository.findByIdAndRestaurantId(MENU_ID_1, RESTAURANT_ID_1);
        checkEntityFieldLoadingType(Menu.class, "dishes", false);
        checkEntityFieldLoadingType(Menu.class, "restaurant", true);
        assertMatch(actual, MENU_1);
    }

    @Test
    void findByIdAndRestaurantIdNotExist() {
        final Menu menuNotExist = menuRepository.findByIdAndRestaurantId(NOT_EXIST_ID, RESTAURANT_ID_1);
        assertNull(menuNotExist);
    }

    @Test
    void findByIdAndRestaurantIdNotOwn() {
        final Menu menuNotExist = menuRepository.findByIdAndRestaurantId(MENU_ID_2, RESTAURANT_ID_1);
        assertNull(menuNotExist);
    }

    @Test
    void findAllByRestaurantIdOrderByRegisteredDesc() {
        final List<Menu> actual = menuRepository.findAllByRestaurantIdOrderByRegisteredDesc(RESTAURANT_ID_1);
        checkEntityFieldLoadingType(Menu.class, "dishes", false);
        assertMatch(actual, MENU_1, MENU_1_NOW);
    }

    @Test
    void saveWithDishes() {
        final Menu menu = getNewMenuWithDishes();
        menu.setRestaurant(RESTAURANT_4_NO_MENU);
        final Menu saved = menuRepository.save(menu);
        checkSaveWithDishes(saved);
    }

    @Test
    void save() {
        final Menu menu = getNewMenu();
        menu.setRestaurant(RESTAURANT_4_NO_MENU);
        final Menu saved = menuRepository.save(menu);
        checkSave(saved);
    }

    @Test
    void update() {
        final Menu menu = getUpdatedMenu(MENU_1);
        final Menu updated = menuRepository.save(menu);
        assertMatch(updated, getUpdatedMenu(MENU_1));
    }
}