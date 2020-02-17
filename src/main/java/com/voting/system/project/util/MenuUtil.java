package com.voting.system.project.util;

import com.voting.system.project.model.Dish;
import com.voting.system.project.model.Menu;
import com.voting.system.project.to.DishTo;
import com.voting.system.project.to.MenuTo;
import com.voting.system.project.to.MenuWithDishesTo;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class MenuUtil {

    private MenuUtil() {
    }

    public static MenuWithDishesTo getToFrom(Menu menu, ModelMapper mapper) {
        final List<DishTo> dishesTos = getDishTos(menu, mapper);
        return new MenuWithDishesTo(menu.getId(), menu.getRegistered(), dishesTos);
    }

    static List<DishTo> getDishTos(Menu menu, ModelMapper mapper) {
        final List<DishTo> dishesTos = new ArrayList<>();
        final List<Dish> dishes = menu.getDishes();
        if (dishes != null && !dishes.isEmpty()) {
            dishes.forEach(dish -> dishesTos.add(mapper.map(dish, DishTo.class)));
        }
        return dishesTos;
    }

    public static MenuTo getToFrom(Menu menu) {
        return new MenuTo(menu.getId(), menu.getRegistered());
    }

    public static Menu getFromTo(MenuTo menuTo) {
        return new Menu(menuTo.getId(), menuTo.getRegistered(), null);
    }
}
