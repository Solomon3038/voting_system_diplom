package com.voting.system.project.util;

import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;

public class RestaurantTestUtil {

    public static void checkAllWithMenusOnCurrentDate(List<Restaurant> actual) {
        assertMatch(actual, RESTAURANTS_WITH_MENU_ON_CURRENT_DATE);
        List<Menu> menus = actual.stream()
                .map(Restaurant::getMenus)
                .flatMap(List::stream)
                .sorted(Comparator.comparing(Menu::getId))
                .collect(Collectors.toList());
        assertMatch(menus, MENUS_NOW);
        for (int i = 0; i < 3; i++) {
            assertMatch(menus.get(i).getDishes(), MENUS_NOW.get(i).getDishes());
        }

    }

    public static void checkSaveWithMenusAndDishes(Restaurant saved) {
        Restaurant expected = getNewRestaurantWithMenuAndDishes();
        int id = saved.getId();
        expected.setId(id);
        int menuId = saved.getMenus().iterator().next().getId();
        expected.getMenus().iterator().next().setId(menuId);
        assertMatch(saved, expected);
        assertMatch(saved.getMenus(), expected.getMenus());
    }

    public static void checkSave(Restaurant saved) {
        Restaurant expected = getNewRestaurant();
        expected.setId(saved.getId());
        assertMatch(saved, expected);
    }

    public static void checkUpdate(Restaurant updated) {
        Restaurant expected = getUpdatedRestaurant(RESTAURANT_1);
        assertMatch(updated, expected);
    }
}