package com.voting.system.project.util.mapper;

import com.voting.system.project.model.MenuItem;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.to.MenuItemDishNameTo;
import com.voting.system.project.to.RestaurantTo;

import java.util.*;

public class RestaurantToUtil {

    public static List<RestaurantTo> convert(List<MenuItem> source, OrikaMapper mapper) {

        Map<Restaurant, RestaurantTo> map = new HashMap<>();

        source.forEach(menuItem -> {
            Restaurant restaurant = menuItem.getDish().getRestaurant();
            RestaurantTo restaurantTo = new RestaurantTo(restaurant, new ArrayList<>(Arrays.asList(mapper.map(menuItem, MenuItemDishNameTo.class))));

            map.merge(menuItem.getDish().getRestaurant(),
                    restaurantTo,
                    (oldItem, newItem) -> {
                        oldItem.getMenuItemDishNameTos().addAll(newItem.getMenuItemDishNameTos());
                        return oldItem;
                    });
        });

        List<RestaurantTo> tos = new ArrayList<>(map.values());
        tos.sort(Comparator.comparing(RestaurantTo::getName));
        tos.forEach(items-> items.getMenuItemDishNameTos().sort(Comparator.comparing(MenuItemDishNameTo::getDishName)));

        return tos;
    }
}