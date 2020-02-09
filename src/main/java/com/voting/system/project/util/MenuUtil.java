package com.voting.system.project.util;

import com.voting.system.project.model.Menu;
import com.voting.system.project.to.DishTo;
import com.voting.system.project.to.MenuWithDishesTo;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class MenuUtil {

    private MenuUtil() {
    }

    public static MenuWithDishesTo getToFrom(Menu menu, ModelMapper mapper) {
        final List<DishTo> dishesTos = new ArrayList<>();
        menu.getDishes().forEach(dish -> dishesTos.add(mapper.map(dish, DishTo.class)));
        return new MenuWithDishesTo(menu.getId(), menu.getRegistered(), dishesTos);
    }
}
