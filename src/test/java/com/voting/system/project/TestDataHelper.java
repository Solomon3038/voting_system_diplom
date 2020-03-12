package com.voting.system.project;

import com.voting.system.project.model.Dish;
import com.voting.system.project.model.MenuItem;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.model.Role;
import com.voting.system.project.model.User;
import com.voting.system.project.model.Vote;
import com.voting.system.project.to.MenuItemDishIdTo;

import java.time.LocalDate;
import java.util.List;

public class TestDataHelper {
    public static final int NOT_EXIST_ID = -1;

    public static final int USER_ID_1 = 1;
    public static final int USER_ID_2 = USER_ID_1 + 1;
    public static final int ADMIN_ID_1 = USER_ID_2 + 1;
    public static final int ADMIN_ID_2 = ADMIN_ID_1 + 1;

    public static final String ADMIN_1_EMAIL = "admin.one@gmail.com";
    public static final String USER_1_EMAIL = "user.one@ukr.net";
    public static final String USER_2_EMAIL = "user.two@ukr.net";

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

    public static final int DISH_ID_1 = 1;
    public static final int DISH_ID_2 = DISH_ID_1 + 1;
    public static final int DISH_ID_3 = DISH_ID_2 + 1;
    public static final int DISH_ID_4 = DISH_ID_3 + 1;
    public static final int DISH_ID_5 = DISH_ID_4 + 1;
    public static final int DISH_ID_6 = DISH_ID_5 + 1;
    public static final int DISH_ID_7 = DISH_ID_6 + 1;
    public static final int DISH_ID_8 = DISH_ID_7 + 1;
    public static final int DISH_ID_9 = DISH_ID_8 + 1;
    public static final int DISH_ID_NEXT = DISH_ID_9 + 1;

    public static final Dish DISH_1 = new Dish(DISH_ID_1, "Шатобріан", RESTAURANT_1);
    public static final Dish DISH_2 = new Dish(DISH_ID_2, "Червоний борщ", RESTAURANT_1);
    public static final Dish DISH_3 = new Dish(DISH_ID_3, "Салат з тигровими креветками під кисло-солодким соусом", RESTAURANT_1);
    public static final Dish DISH_4 = new Dish(DISH_ID_4, "Карпаччо з лосося", RESTAURANT_2);
    public static final Dish DISH_5 = new Dish(DISH_ID_5, "Салат цезар", RESTAURANT_2);
    public static final Dish DISH_6 = new Dish(DISH_ID_6, "Хінкалі з баранини", RESTAURANT_2);
    public static final Dish DISH_7 = new Dish(DISH_ID_7, "Шашлик із телятини", RESTAURANT_3);
    public static final Dish DISH_8 = new Dish(DISH_ID_8, "Курча тапака", RESTAURANT_3);
    public static final Dish DISH_9 = new Dish(DISH_ID_9, "Торт", RESTAURANT_3);

    public static final int MENU_ID_1 = 1;
    public static final int MENU_ID_2 = MENU_ID_1 + 1;
    public static final int MENU_ID_3 = MENU_ID_2 + 1;
    public static final int MENU_ID_4 = MENU_ID_3 + 1;
    public static final int MENU_ID_5 = MENU_ID_4 + 1;
    public static final int MENU_ID_6 = MENU_ID_5 + 1;
    public static final int MENU_ID_7 = MENU_ID_6 + 1;
    public static final int MENU_ID_8 = MENU_ID_7 + 1;
    public static final int MENU_ID_9 = MENU_ID_8 + 1;
    public static final int MENU_ID_10 = MENU_ID_9 + 1;
    public static final int MENU_ID_11 = MENU_ID_10 + 1;
    public static final int MENU_ID_12 = MENU_ID_11 + 1;
    public static final int MENU_ID_13 = MENU_ID_12 + 1;
    public static final int MENU_ID_14 = MENU_ID_13 + 1;
    public static final int MENU_ID_NEXT = MENU_ID_14 + 1;

    public static final MenuItem MENU_1_1 = new MenuItem(MENU_ID_1, LocalDate.of(2019, 6, 1), 90_00, DISH_1, RESTAURANT_1);
    public static final MenuItem MENU_1_2 = new MenuItem(MENU_ID_2, LocalDate.of(2019, 6, 1), 38_00, DISH_2, RESTAURANT_1);
    public static final MenuItem MENU_1_3 = new MenuItem(MENU_ID_3, LocalDate.of(2019, 6, 1), 146_00, DISH_3, RESTAURANT_1);
    public static final MenuItem MENU_2_1 = new MenuItem(MENU_ID_4, LocalDate.of(2019, 6, 1), 99_98, DISH_4, RESTAURANT_2);
    public static final MenuItem MENU_2_2 = new MenuItem(MENU_ID_5, LocalDate.of(2019, 6, 1), 110_50, DISH_5, RESTAURANT_2);
    public static final MenuItem MENU_2_3 = new MenuItem(MENU_ID_6, LocalDate.of(2019, 6, 1), 97_00, DISH_6, RESTAURANT_2);
    public static final MenuItem MENU_3_1 = new MenuItem(MENU_ID_7, LocalDate.of(2019, 6, 1), 85_00, DISH_7, RESTAURANT_3);
    public static final MenuItem MENU_3_2 = new MenuItem(MENU_ID_8, LocalDate.of(2019, 6, 1), 70_00, DISH_8, RESTAURANT_3);
    public static final MenuItem MENU_3_3 = new MenuItem(MENU_ID_9, LocalDate.of(2019, 6, 1), 30_000, DISH_9, RESTAURANT_3);

    public static final MenuItem MENU_1_1_NOW = new MenuItem(MENU_ID_10, 90_00, DISH_1, RESTAURANT_1);
    public static final MenuItem MENU_1_2_NOW = new MenuItem(MENU_ID_11, 38_00, DISH_2, RESTAURANT_1);
    public static final MenuItem MENU_2_1_NOW = new MenuItem(MENU_ID_12, 99_98, DISH_4, RESTAURANT_2);
    public static final MenuItem MENU_2_2_NOW = new MenuItem(MENU_ID_13, 97_00, DISH_6, RESTAURANT_2);
    public static final MenuItem MENU_3_1_NOW = new MenuItem(MENU_ID_14, 30_000, DISH_9, RESTAURANT_3);

    public static final List<Restaurant> RESTAURANTS_WITH_MENU_ON_CURRENT_DATE = List.of(RESTAURANT_2, RESTAURANT_1, RESTAURANT_3);
    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT_4_NO_MENU, RESTAURANT_2, RESTAURANT_1, RESTAURANT_3);

    public static final List<MenuItem> RESTAURANT_1_MENUS = List.of(MENU_1_2_NOW, MENU_1_1_NOW, MENU_1_3, MENU_1_2, MENU_1_1);
    public static final List<MenuItem> RESTAURANT_2_MENUS = List.of(MENU_2_1_NOW, MENU_2_2_NOW, MENU_2_1, MENU_2_2, MENU_2_3);
    public static final List<MenuItem> RESTAURANT_3_MENUS = List.of(MENU_3_1_NOW, MENU_3_2, MENU_3_3, MENU_3_1);

    public static final List<MenuItem> RESTAURANT_1_MENUS_NOW = List.of(MENU_1_2_NOW, MENU_1_1_NOW);
    public static final List<MenuItem> RESTAURANT_2_MENUS_NOW = List.of(MENU_2_1_NOW, MENU_2_2_NOW);
    public static final List<MenuItem> RESTAURANT_3_MENUS_NOW = List.of(MENU_3_1_NOW);

    public static final List<Dish> DISHES = List.of(DISH_4, DISH_8, DISH_3, DISH_9, DISH_5, DISH_6, DISH_2, DISH_1, DISH_7);

    public static final int VOTE_ID_1 = 1;

    public static final Vote VOTE_USER_2 = new Vote(VOTE_ID_1, RESTAURANT_1, USER_2);

    static {
        RESTAURANT_1.setMenus(RESTAURANT_1_MENUS_NOW);
        RESTAURANT_2.setMenus(RESTAURANT_2_MENUS_NOW);
        RESTAURANT_3.setMenus(RESTAURANT_3_MENUS_NOW);
    }

    private TestDataHelper() {
    }

    public static Restaurant getNewRestaurant() {
        return new Restaurant(null, "new name", "new address");
    }

    public static Restaurant getUpdatedRestaurant(Restaurant restaurant) {
        return new Restaurant(restaurant.getId(), "updated name", restaurant.getAddress(), restaurant.getMenuItems());
    }

    public static Dish getNewDish() {
        return new Dish(null, "new name", RESTAURANT_1);
    }

    public static Dish getUpdatedDish(Dish dish) {
        return new Dish(dish.getId(), "updated name", RESTAURANT_1);
    }

    public static MenuItemDishIdTo getNewMenuItemDishIdTo() {
        MenuItemDishIdTo to = new MenuItemDishIdTo();
        to.setDishId(5);
        to.setPrice(56_00);
        return to;
    }

    public static MenuItemDishIdTo getUpdatedMenuItemDishIdTo(MenuItem menuItem) {
        return new MenuItemDishIdTo(menuItem.getId(), LocalDate.of(2019, 7, 12),
                menuItem.getPrice(), menuItem.getDish().getId());
    }

    public static Vote getNewVote() {
        return new Vote(null, RESTAURANT_2, USER_1);
    }

    public static Vote getUpdatedVote(Vote vote) {
        return new Vote(vote.getId(), RESTAURANT_3, vote.getUser());
    }
}
