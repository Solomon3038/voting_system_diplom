package com.voting.system.project.util;

import com.voting.system.project.model.Dish;
import com.voting.system.project.model.HasId;
import com.voting.system.project.model.MenuItem;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.model.User;
import com.voting.system.project.to.MenuItemDishIdTo;
import com.voting.system.project.to.MenuItemDishNameTo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMatcherUtil {
    public static final Map<Class<? extends HasId>, String[]> ignoreFieldsMap = new HashMap<>();

    static {
        ignoreFieldsMap.put(User.class, new String[]{"registered"});
        ignoreFieldsMap.put(Restaurant.class, new String[]{"menuItems"});
        ignoreFieldsMap.put(MenuItem.class, new String[]{"dish", "restaurant"});
        ignoreFieldsMap.put(MenuItemDishIdTo.class, new String[]{"dishId"});
        ignoreFieldsMap.put(MenuItemDishNameTo.class, new String[]{"dishName"});
        ignoreFieldsMap.put(Dish.class, new String[]{"restaurant"});
    }

    private TestMatcherUtil() {
    }

    public static <T extends HasId> void assertMatch(T actual, T expected) {
        final String[] ignoredFields = ignoreFieldsMap.getOrDefault(actual.getClass(), new String[0]);
        assertThat(actual).isEqualToIgnoringGivenFields(expected, ignoredFields);
    }

    @SafeVarargs
    public static <T> void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static <T> void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        final String[] ignoredFields = ignoreFieldsMap.getOrDefault(actual.iterator().next().getClass(), new String[0]);
        assertThat(actual).usingElementComparatorIgnoringFields(ignoredFields).isEqualTo(expected);
    }
}
