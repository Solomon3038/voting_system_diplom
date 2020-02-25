package com.voting.system.project;

import com.voting.system.project.model.Dish;
import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.model.Role;
import com.voting.system.project.model.User;
import com.voting.system.project.model.Vote;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestDataHelper {
    public static final String ADMIN_1_EMAIL = "admin.one@gmail.com";
    public static final String USER_1_EMAIL = "user.one@ukr.net";
    public static final String USER_2_EMAIL = "user.two@ukr.net";

    public static final int NOT_EXIST_ID = -1;

    public static final int USER_ID_1 = 1;
    public static final int USER_ID_2 = USER_ID_1 + 1;
    public static final int ADMIN_ID_1 = USER_ID_2 + 1;
    public static final int ADMIN_ID_2 = ADMIN_ID_1 + 1;

    public static final User USER_1 = new User(USER_ID_1, "User One", USER_1_EMAIL, "{noop}password", Role.ROLE_USER);
    public static final User USER_2 = new User(USER_ID_2, "User Two", USER_2_EMAIL, "{noop}password", Role.ROLE_USER);
    public static final User ADMIN_1 = new User(ADMIN_ID_1, "Admin One", ADMIN_1_EMAIL, "{noop}admin", Role.ROLE_ADMIN);
    public static final User ADMIN_2 = new User(ADMIN_ID_2, "Admin Two", "admin.two@gmail.com", "{noop}admin", Role.ROLE_ADMIN);

    public static final int RESTAURANT_ID_1 = 1;
    public static final int RESTAURANT_ID_2 = RESTAURANT_ID_1 + 1;
    public static final int RESTAURANT_ID_3 = RESTAURANT_ID_2 + 1;
    public static final int RESTAURANT_ID_4 = RESTAURANT_ID_3 + 1;
    public static final int RESTAURANT_ID_NEXT = RESTAURANT_ID_4 + 1;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_ID_1, "Manhattan-skybar", "вулиця Соборна, 112, Рівне, Рівненська область, 33000");
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_ID_2, "Gastro", "проспект Миру, 10, Рівне, Рівненська область, 33013");
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_ID_3, "Vinograd", "вулиця Видумка, 2 Б, Рівне, Рівненська область, 33023");
    public static final Restaurant RESTAURANT_4_NO_MENU = new Restaurant(RESTAURANT_ID_4, "Closed", "вулиця Грушевського, 120, Рівне, Рівненська область, 33000");

    public static final int MENU_ID_1 = 1;
    public static final int MENU_ID_2 = MENU_ID_1 + 1;
    public static final int MENU_ID_3 = MENU_ID_2 + 1;
    public static final int MENU_ID_4 = MENU_ID_3 + 1;
    public static final int MENU_ID_5 = MENU_ID_4 + 1;
    public static final int MENU_ID_6 = MENU_ID_5 + 1;
    public static final int MENU_ID_NEXT = MENU_ID_6 + 1;

    public static final Menu MENU_1 = new Menu(MENU_ID_1, LocalDate.of(2021, 6, 1), RESTAURANT_1);
    public static final Menu MENU_2 = new Menu(MENU_ID_2, LocalDate.of(2021, 6, 1), RESTAURANT_2);
    public static final Menu MENU_3 = new Menu(MENU_ID_3, LocalDate.of(2021, 6, 1), RESTAURANT_3);

    public static final Menu MENU_1_NOW = new Menu(MENU_ID_4, RESTAURANT_1);
    public static final Menu MENU_2_NOW = new Menu(MENU_ID_5, RESTAURANT_2);
    public static final Menu MENU_3_NOW = new Menu(MENU_ID_6, RESTAURANT_3);

    public static final int DISH_ID_1 = 1;
    public static final int DISH_ID_2 = DISH_ID_1 + 1;
    public static final int DISH_ID_3 = DISH_ID_2 + 1;
    public static final int DISH_ID_4 = DISH_ID_3 + 1;
    public static final int DISH_ID_5 = DISH_ID_4 + 1;
    public static final int DISH_ID_6 = DISH_ID_5 + 1;
    public static final int DISH_ID_7 = DISH_ID_6 + 1;
    public static final int DISH_ID_8 = DISH_ID_7 + 1;
    public static final int DISH_ID_9 = DISH_ID_8 + 1;
    public static final int DISH_ID_10 = DISH_ID_9 + 1;
    public static final int DISH_ID_11 = DISH_ID_10 + 1;
    public static final int DISH_ID_12 = DISH_ID_11 + 1;
    public static final int DISH_ID_13 = DISH_ID_12 + 1;
    public static final int DISH_ID_14 = DISH_ID_13 + 1;
    public static final int DISH_ID_15 = DISH_ID_14 + 1;
    public static final int DISH_ID_NEXT = DISH_ID_15 + 1;

    public static final Dish DISH_1_1 = new Dish(DISH_ID_1, "Шатобріан", 99_00, MENU_1);
    public static final Dish DISH_1_2 = new Dish(DISH_ID_2, "Червоний борщ", 38_00, MENU_1);
    public static final Dish DISH_1_3 = new Dish(DISH_ID_3, "Салат з тигровими креветками під кисло-солодким соусом", 146_00, MENU_1);

    public static final Dish DISH_2_1 = new Dish(DISH_ID_4, "Карпаччо з лосося", 99_98, MENU_2);
    public static final Dish DISH_2_2 = new Dish(DISH_ID_5, "Салат цезар", 110_50, MENU_2);

    public static final Dish DISH_3_1 = new Dish(DISH_ID_6, "Хінкалі з баранини", 97_00, MENU_3);
    public static final Dish DISH_3_2 = new Dish(DISH_ID_7, "Шашлик із телятини", 85_00, MENU_3);
    public static final Dish DISH_3_3 = new Dish(DISH_ID_8, "Курча тапака", 70_00, MENU_3);

    public static final Dish DISH_1_1_NOW = new Dish(DISH_ID_9, "Шатобріан", 99_00, MENU_1_NOW);
    public static final Dish DISH_1_2_NOW = new Dish(DISH_ID_10, "Червоний борщ", 38_00, MENU_1_NOW);
    public static final Dish DISH_1_3_NOW = new Dish(DISH_ID_11, "Салат з тигровими креветками під кисло-солодким соусом", 146_00, MENU_1_NOW);

    public static final Dish DISH_2_1_NOW = new Dish(DISH_ID_12, "Карпаччо з лосося", 99_98, MENU_2_NOW);

    public static final Dish DISH_3_1_NOW = new Dish(DISH_ID_13, "Салат цезар", 110_50, MENU_3_NOW);
    public static final Dish DISH_3_2_NOW = new Dish(DISH_ID_14, "Хінкалі з баранини", 97_00, MENU_3_NOW);
    public static final Dish DISH_3_3_NOW = new Dish(DISH_ID_15, "Курча тапака", 70_00, MENU_3_NOW);

    public static final int VOTE_ID_1 = 1;

    public static final Vote VOTE_USER_2 = new Vote(VOTE_ID_1, RESTAURANT_1, USER_2);

    public static final List<Restaurant> RESTAURANTS_WITH_MENU_ON_CURRENT_DATE = List.of(RESTAURANT_2, RESTAURANT_1, RESTAURANT_3);
    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT_4_NO_MENU, RESTAURANT_2, RESTAURANT_1, RESTAURANT_3);

    public static final List<Menu> MENUS_NOW = List.of(MENU_1_NOW, MENU_2_NOW, MENU_3_NOW);
    public static final List<Menu> RESTAURANT_1_MENUS = List.of(MENU_1, MENU_1_NOW);

    public static final List<Dish> RESTAURANT_1_MENU_1_DISHES = List.of(DISH_1_3, DISH_1_2, DISH_1_1);
    public static final List<Dish> RESTAURANT_1_MENU_1_NOW_DISHES = List.of(DISH_1_3_NOW, DISH_1_2_NOW, DISH_1_1_NOW);

    static {
        MENU_1.setDishes(Arrays.asList(DISH_1_1, DISH_1_2, DISH_1_3));
        MENU_2.setDishes(Arrays.asList(DISH_2_1, DISH_2_2));
        MENU_3.setDishes(Arrays.asList(DISH_3_1, DISH_3_2, DISH_3_3));
        MENU_1_NOW.setDishes(Arrays.asList(DISH_1_3_NOW, DISH_1_2_NOW, DISH_1_1_NOW));
        MENU_2_NOW.setDishes(Collections.singletonList(DISH_2_1_NOW));
        MENU_3_NOW.setDishes(Arrays.asList(DISH_3_3_NOW, DISH_3_1_NOW, DISH_3_2_NOW));

        RESTAURANT_1.setMenus(Arrays.asList(MENU_1_NOW));
        RESTAURANT_2.setMenus(Arrays.asList(MENU_2_NOW));
        RESTAURANT_3.setMenus(Arrays.asList(MENU_3_NOW));
    }

    private TestDataHelper() {
    }

    public static Restaurant getNewRestaurantWithMenu() {
        final Restaurant restaurant = new Restaurant(null, "New Restaurant", "new Address");
        final Menu menu = new Menu(null, restaurant);
        restaurant.setMenu(menu);
        return restaurant;
    }

    public static Restaurant getNewRestaurantWithMenuAndDishes() {
        final Restaurant restaurant = new Restaurant(null, "New Restaurant", "new Address");
        final Menu menu = new Menu(null, restaurant);
        final Dish dish1 = new Dish(null, "dish 1", 10_00, menu);
        final Dish dish2 = new Dish(null, "dish 2", 20_00, menu);
        menu.setDishes(Arrays.asList(dish1, dish2));
        restaurant.setMenu(menu);
        return restaurant;
    }

    public static Restaurant getNewRestaurant() {
        return new Restaurant(null, "New Restaurant", "new Address");
    }

    public static Restaurant getUpdatedRestaurant(Restaurant restaurant) {
        return new Restaurant(restaurant.getId(), "Update Restaurant", restaurant.getAddress());
    }

    public static Menu getNewMenu() {
        return new Menu(null, LocalDate.of(2020, 3, 1), null);
    }

    public static Menu getUpdatedMenu(Menu updated) {
        return new Menu(updated.getId(), LocalDate.of(2020, 3, 1), updated.getRestaurant());
    }

    public static Menu getNewMenuWithDishes() {
        final Menu menu = new Menu(null, LocalDate.of(2020, 3, 1), null);
        final Dish dish = new Dish(null, "dish 1", 10_00, menu);
        menu.setDishes(Collections.singletonList(dish));
        return menu;
    }

    public static Dish getNewDish() {
        return new Dish(null, "dish new", 103_00, null);
    }

    public static Dish getUpdatedDish(Dish updated) {
        return new Dish(updated.getId(), "dish update", 103_00, updated.getMenu());
    }

    public static Vote getNewVote() {
        return new Vote(null, RESTAURANT_2, USER_1);
    }

    public static Vote getUpdatedVote(Vote vote) {
        return new Vote(vote.getId(), RESTAURANT_3, vote.getUser());
    }
}