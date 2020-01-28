package com.voting.system.project.util;

import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.model.User;
import com.voting.system.project.model.Vote;
import com.voting.system.project.to.DishTo;
import com.voting.system.project.to.MenuWithDishesTo;
import com.voting.system.project.to.RestaurantWithMenusTo;
import com.voting.system.project.to.VoteTo;
import org.modelmapper.ModelMapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {

    public static Vote getFromTo(VoteTo voteTo, User user, Restaurant restaurant) {
        return new Vote(voteTo.getId(), voteTo.getDate(), restaurant, user);
    }

    public static RestaurantWithMenusTo getToFrom(Restaurant restaurant, ModelMapper mapper) {
        final List<Menu> menus = restaurant.getMenus();
        final List<MenuWithDishesTo> menusTo = menus.stream()
                .map(menu -> {
            final List<DishTo> dishesTo = menu.getDishes().stream()
                    .map(dish -> mapper.map(dish, DishTo.class))
                    .sorted(Comparator.comparing(DishTo::getName))
                    .collect(Collectors.toList());
            return new MenuWithDishesTo(menu.getId(), dishesTo);
        }).collect(Collectors.toList());
        return new RestaurantWithMenusTo(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), menusTo);
    }
}
