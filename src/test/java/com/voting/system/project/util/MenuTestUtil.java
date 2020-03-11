package com.voting.system.project.util;

public class MenuTestUtil {

    private MenuTestUtil() {
    }

//    public static void checkWithDishes(Menu actual) {
//        assertMatch(actual, MENU_1);
//        final List<Dish> dishes = actual.getDishes().stream()
//                .sorted(Comparator.comparing(Dish::getName))
//                .collect(Collectors.toList());
//        assertMatch(dishes, RESTAURANT_1_MENU_1_DISHES);
//    }
//
//    public static void checkAllWithDishes(List<Menu> actual) {
//        assertMatch(actual, RESTAURANT_1_MENUS);
//        final List<Dish> dishesNow = actual.get(0).getDishes().stream()
//                .sorted(Comparator.comparing(Dish::getName))
//                .collect(Collectors.toList());
//        assertMatch(dishesNow, RESTAURANT_1_MENU_1_DISHES);
//        final List<Dish> dishes = actual.get(1).getDishes().stream()
//                .sorted(Comparator.comparing(Dish::getName))
//                .collect(Collectors.toList());
//        assertMatch(dishes, RESTAURANT_1_MENU_1_NOW_DISHES);
//    }
//
//    public static void checkSaveWithDishes(Menu saved) {
//        final Menu expected = getNewMenuWithDishes();
//        expected.setId(saved.getId());
//        assertMatch(saved, expected);
//
//        final int id = saved.getDishes().iterator().next().getId();
//        expected.getDishes().iterator().next().setId(id);
//        assertMatch(saved.getDishes(), expected.getDishes());
//    }
//
//    public static void checkSave(Menu saved) {
//        final Menu expected = getNewMenu();
//        expected.setId(saved.getId());
//        assertMatch(saved, expected);
//    }
}