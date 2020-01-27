package com.voting.system.project.util;

import com.voting.system.project.model.Dish;
import com.voting.system.project.model.Menu;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.TestMatcherUtil.assertMatch;

public class MenuTestUtil {

    public static void checkWithDishes(Menu actual) {
        assertMatch(actual, MENU_1);
        List<Dish> dishes = actual.getDishes().stream()
                .sorted(Comparator.comparing(Dish::getName))
                .collect(Collectors.toList());
        assertMatch(dishes, RESTAURANT_1_MENU_1_DISHES);
    }

    public static void checkAllWithDishes(List<Menu> actual) {
        assertMatch(actual, RESTAURANT_1_MENUS);
        List<Dish> dishesNow = actual.get(0).getDishes().stream()
                .sorted(Comparator.comparing(Dish::getName))
                .collect(Collectors.toList());
        assertMatch(dishesNow, RESTAURANT_1_MENU_1_NOW_DISHES);
        List<Dish> dishes = actual.get(1).getDishes().stream()
                .sorted(Comparator.comparing(Dish::getName))
                .collect(Collectors.toList());
        assertMatch(dishes, RESTAURANT_1_MENU_1_DISHES);
    }

    public static void checkSaveWithDishes(Menu saved) {
        Menu expected = getNewMenuWithDishes();
        expected.setId(saved.getId());
        assertMatch(saved, expected);
        Assertions.assertEquals(saved.getRegistered(), LocalDate.now());

        int id = saved.getDishes().iterator().next().getId();
        expected.getDishes().iterator().next().setId(id);
        assertMatch(saved.getDishes(), expected.getDishes());
    }
}