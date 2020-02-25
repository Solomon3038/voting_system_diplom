package com.voting.system.project.util;

import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.voting.system.project.TestDataHelper.MENUS_NOW;
import static com.voting.system.project.TestDataHelper.RESTAURANTS_WITH_MENU_ON_CURRENT_DATE;
import static com.voting.system.project.TestDataHelper.RESTAURANT_1;
import static com.voting.system.project.TestDataHelper.getNewRestaurant;
import static com.voting.system.project.TestDataHelper.getNewRestaurantWithMenu;
import static com.voting.system.project.TestDataHelper.getNewRestaurantWithMenuAndDishes;
import static com.voting.system.project.TestDataHelper.getUpdatedRestaurant;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;

public class RestaurantTestUtil {

    private RestaurantTestUtil() {
    }

    public static void checkAllWithMenusOnCurrentDate(List<Restaurant> actual) {
        assertMatch(actual, RESTAURANTS_WITH_MENU_ON_CURRENT_DATE);
        final List<Menu> menus = actual.stream()
                .map(Restaurant::getMenus)
                .flatMap(List::stream)
                .sorted(Comparator.comparing(Menu::getId))
                .collect(Collectors.toList());
        assertMatch(menus, MENUS_NOW);
        for (int i = 0; i < 3; i++) {
            assertMatch(menus.get(i).getDishes(), MENUS_NOW.get(i).getDishes());
        }
    }

    public static void checkSaveWithMenu(Restaurant saved) {
        final Restaurant expected = getNewRestaurantWithMenu();
        checkSaveWith(saved, expected);
    }

    public static void checkSaveWithMenuAndDishes(Restaurant saved) {
        final Restaurant expected = getNewRestaurantWithMenuAndDishes();
        checkSaveWith(saved, expected);
    }

    public static void checkSave(Restaurant saved) {
        final Restaurant expected = getNewRestaurant();
        expected.setId(saved.getId());
        assertMatch(saved, expected);
    }

    public static void checkUpdate(Restaurant updated) {
        final Restaurant expected = getUpdatedRestaurant(RESTAURANT_1);
        assertMatch(updated, expected);
    }

    private static void checkSaveWith(Restaurant saved, Restaurant expected) {
        final int id = saved.getId();
        expected.setId(id);
        final int menuId = saved.getMenus().iterator().next().getId();
        expected.getMenus().iterator().next().setId(menuId);
        assertMatch(saved, expected);
        assertMatch(saved.getMenus(), expected.getMenus());
    }
}