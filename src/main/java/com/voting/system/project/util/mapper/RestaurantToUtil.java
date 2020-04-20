package com.voting.system.project.util.mapper;

import com.voting.system.project.model.MenuItem;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.to.MenuItemDishNameTo;
import com.voting.system.project.to.RestaurantTo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantToUtil {

    public static List<RestaurantTo> convert(List<MenuItem> source, OrikaMapper mapper) {

        Map<Integer, RestaurantTo> map = new HashMap<>();

        source.forEach(menuItem -> {
            Restaurant restaurant = menuItem.getDish().getRestaurant();
            RestaurantTo restaurantTo = new RestaurantTo(restaurant, new ArrayList<>(Collections.singletonList(mapper.map(menuItem, MenuItemDishNameTo.class))));

            map.merge(restaurant.getId(), restaurantTo,
                    (oldItem, newItem) -> {
                        oldItem.getMenuItemDishNameTos().addAll(newItem.getMenuItemDishNameTos());
                        return oldItem;
                    });
        });

        List<RestaurantTo> tos = new ArrayList<>(map.values());
        tos.sort(Comparator.comparing(RestaurantTo::getName));
        tos.forEach(items -> items.getMenuItemDishNameTos().sort(Comparator.comparing(MenuItemDishNameTo::getDishName)));

        return tos;
    }
}