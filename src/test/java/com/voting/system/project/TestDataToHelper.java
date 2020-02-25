package com.voting.system.project;

import com.voting.system.project.model.Dish;
import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.to.DishTo;
import com.voting.system.project.to.MenuTo;
import com.voting.system.project.to.MenuWithDishesTo;
import com.voting.system.project.to.RestaurantTo;
import com.voting.system.project.to.RestaurantWithMenusTo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

public class TestDataToHelper {

    private TestDataToHelper() {
    }

    public static RestaurantWithMenusTo getNewRestaurantWithMenuTo() {
        final RestaurantWithMenusTo restaurant = new RestaurantWithMenusTo(null, "New Restaurant", "new Address");
        final MenuWithDishesTo menu = new MenuWithDishesTo(null);
        restaurant.setMenus(Arrays.asList(menu));
        return restaurant;
    }

    public static RestaurantWithMenusTo getNewRestaurantWithMenuAndDishesTo() {
        final RestaurantWithMenusTo restaurant = new RestaurantWithMenusTo(null, "New Restaurant", "new Address");
        final MenuWithDishesTo menu = new MenuWithDishesTo(null);
        final DishTo dish1 = new DishTo(null, "dish 1", 10_00);
        final DishTo dish2 = new DishTo(null, "dish 2", 20_00);
        menu.setDishes(Arrays.asList(dish1, dish2));
        restaurant.setMenus(Arrays.asList(menu));
        return restaurant;
    }

    public static RestaurantWithMenusTo getInvalidNewRestaurantWithMenuAndDishesTo() {
        final RestaurantWithMenusTo restaurant = new RestaurantWithMenusTo(null, "New Restaurant", "new Address");
        final MenuWithDishesTo menu = new MenuWithDishesTo(null);
        final DishTo dish1 = new DishTo(null, "", 0);
        menu.setDishes(Arrays.asList(dish1));
        restaurant.setMenus(Arrays.asList(menu));
        return restaurant;
    }

    public static RestaurantTo getNewRestaurantTo() {
        return new RestaurantTo(null, "New Restaurant", "new Address");
    }

    public static RestaurantTo getUpdatedRestaurantTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), "Update Restaurant", restaurant.getAddress());
    }

    public static MenuTo getNewMenuTo() {
        return new MenuTo(null, LocalDate.of(2020, 3, 1));
    }

    public static MenuTo getUpdatedMenuTo(Menu updated) {
        return new MenuTo(updated.getId(), LocalDate.of(2020, 3, 1));
    }

    public static MenuWithDishesTo getNewMenuWithDishesTo() {
        final MenuWithDishesTo menu = new MenuWithDishesTo(null, LocalDate.of(2020, 3, 1));
        final DishTo dish = new DishTo(null, "dish 1", 10_00);
        menu.setDishes(Collections.singletonList(dish));
        return menu;
    }

    public static MenuWithDishesTo getInvalidNewMenuWithDishesTo() {
        final MenuWithDishesTo menu = new MenuWithDishesTo(null, LocalDate.of(2020, 3, 1));
        final DishTo dish = new DishTo(null, "d", 10_00);
        menu.setDishes(Collections.singletonList(dish));
        return menu;
    }

    public static DishTo getNewDishTo() {
        return new DishTo(null, "dish new", 103_00);
    }

    public static DishTo getUpdatedDishTo(Dish updated) {
        return new DishTo(updated.getId(), "dish update", 103_00);
    }
}