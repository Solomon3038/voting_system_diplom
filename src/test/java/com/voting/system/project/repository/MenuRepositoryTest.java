package com.voting.system.project.repository;

import com.voting.system.project.AbstractProjectApplicationTests;
import com.voting.system.project.model.Dish;
import com.voting.system.project.model.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.TestData.getNewMenuWithDishes;
import static com.voting.system.project.TestMatcherUtil.assertMatch;

class MenuRepositoryTest extends AbstractProjectApplicationTests {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void findAll() {
        List<Menu> actual = menuRepository.findAllByRestaurantId(RESTAURANT_ID_1);
        assertMatch(actual, RESTAURANT_1_MENUS);
        checkEntityFieldLoadingType(Menu.class, "dishes", true);
    }

    @Test
    void findAllWithDishes() {
        List<Menu> actual = menuRepository.findAllByRestaurantIdWithDishes(RESTAURANT_ID_1);
        assertMatch(actual, RESTAURANT_1_MENUS);
        checkEntityFieldLoadingType(Menu.class, "dishes", false);

        List<Dish> dishesNow = actual.get(0).getDishes().stream()
                .sorted(Comparator.comparing(Dish::getName))
                .collect(Collectors.toList());
        assertMatch(dishesNow, RESTAURANT_1_MENU_1_NOW_DISHES);
        List<Dish> dishes = actual.get(1).getDishes().stream()
                .sorted(Comparator.comparing(Dish::getName))
                .collect(Collectors.toList());
        assertMatch(dishes, RESTAURANT_1_MENU_1_DISHES);
    }

    @Test
    void findByIdWithDishes() {
        Menu actual = menuRepository.findByIdWithDishes(MENU_ID_1);
        assertMatch(actual, MENU_1);
        checkEntityFieldLoadingType(Menu.class, "dishes", false);
        List<Dish> dishes = actual.getDishes().stream()
                .sorted(Comparator.comparing(Dish::getName))
                .collect(Collectors.toList());
        assertMatch(dishes, RESTAURANT_1_MENU_1_DISHES);
    }

    @Test
    void findByIdWithDishesNotExist() {
        Menu menuNotExist = menuRepository.findByIdWithDishes(NOT_EXIST_ID);
        Assertions.assertNull(menuNotExist);
    }

    @Test
    void saveWithDishes() {
        Menu saved = menuRepository.save(getNewMenuWithDishes());
        Menu expected = getNewMenuWithDishes();
        expected.setId(saved.getId());
        assertMatch(saved, expected);

        int id = saved.getDishes().iterator().next().getId();
        expected.getDishes().iterator().next().setId(id);
        assertMatch(saved.getDishes(), expected.getDishes());
    }
}