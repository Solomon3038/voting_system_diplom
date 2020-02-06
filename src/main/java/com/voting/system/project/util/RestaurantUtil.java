package com.voting.system.project.util;

import com.voting.system.project.model.Restaurant;
import com.voting.system.project.to.DishTo;
import com.voting.system.project.to.MenuWithDishesTo;
import com.voting.system.project.to.RestaurantWithMenusTo;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class RestaurantUtil {

    private RestaurantUtil() {
    }

    public static RestaurantWithMenusTo getToFrom(Restaurant restaurant, ModelMapper mapper) {
        final List<MenuWithDishesTo> menuTos = new ArrayList<>();
        restaurant.getMenus().forEach(menu -> {
            final List<DishTo> dishesTos = new ArrayList<>();
            menu.getDishes().forEach(dish -> dishesTos.add(mapper.map(dish, DishTo.class)));
            menuTos.add(new MenuWithDishesTo(menu.getId(), menu.getRegistered(), dishesTos));
        });
        return new RestaurantWithMenusTo(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), menuTos);
    }
}
