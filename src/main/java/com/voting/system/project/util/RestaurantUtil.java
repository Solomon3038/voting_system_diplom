package com.voting.system.project.util;

import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.to.DishTo;
import com.voting.system.project.to.MenuWithDishesTo;
import com.voting.system.project.to.RestaurantTo;
import com.voting.system.project.to.RestaurantWithMenusTo;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static com.voting.system.project.util.MenuUtil.getDishTos;

public class RestaurantUtil {

    private RestaurantUtil() {
    }

    public static RestaurantWithMenusTo getToFrom(Restaurant restaurant, ModelMapper mapper) {
        final List<MenuWithDishesTo> menuTos = new ArrayList<>();
        final List<Menu> menus = restaurant.getMenus();
        if (menus != null && !menus.isEmpty()) {
            menus.forEach(menu -> {
                final List<DishTo> dishesTos = getDishTos(menu, mapper);
                menuTos.add(new MenuWithDishesTo(menu.getId(), menu.getRegistered(), dishesTos));
            });
        }
        return new RestaurantWithMenusTo(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), menuTos);
    }

    public static Restaurant getFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName(), restaurantTo.getAddress());
    }
}
