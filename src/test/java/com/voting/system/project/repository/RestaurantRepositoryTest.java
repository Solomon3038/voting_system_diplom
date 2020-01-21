package com.voting.system.project.repository;

import com.voting.system.project.ProjectApplicationTests;
import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.TestMatcherUtil.assertMatch;

class RestaurantRepositoryTest extends ProjectApplicationTests {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void findById() {
        Restaurant actual = restaurantRepository.findById(RESTAURANT_ID_1);
        assertMatch(actual, RESTAURANT_1);
        checkEntityFieldLoadingType(Restaurant.class, "menus", true);
    }

    @Test
    void findByIdNotExist() {
        Restaurant actual = restaurantRepository.findById(NOT_EXIST_ID);
        Assertions.assertNull(actual);
    }

    @Test
    void findAll() {
        List<Restaurant> actual = restaurantRepository.findAll();
        assertMatch(actual, RESTAURANTS);
        checkEntityFieldLoadingType(Restaurant.class, "menus", true);
    }

    @Test
    void findAllWithMenusOnCurrentDate() {
        List<Restaurant> actual = restaurantRepository.findAllWithMenusOnCurrentDate();
        assertMatch(actual, RESTAURANTS_WITH_MENU_ON_CURRENT_DATE);
        checkEntityFieldLoadingType(Restaurant.class, "menus", false);
        List<Menu> menus = actual.stream()
                .map(Restaurant::getMenus)
                .flatMap(Set::stream)
                .sorted(Comparator.comparing(Menu::getId))
                .collect(Collectors.toList());
        assertMatch(menus, MENUS_NOW);
    }

    @Test
    void saveWithMenusAndDishes() {
        Restaurant saved = restaurantRepository.save(getNewRestaurantWithMenuAndDishes());
        Restaurant expected = getNewRestaurantWithMenuAndDishes();
        int id = saved.getId();
        expected.setId(id);
        int menuId = saved.getMenus().iterator().next().getId();
        expected.getMenus().iterator().next().setId(menuId);
        assertMatch(saved, expected);
        assertMatch(saved.getMenus(), expected.getMenus());
    }

    @Test
    void save() {
        Restaurant saved = restaurantRepository.save(getNewRestaurant());
        Restaurant expected = getNewRestaurant();
        expected.setId(saved.getId());
        assertMatch(saved, expected);
    }

    @Test
    void update() {
        Restaurant updated = restaurantRepository.save(getUpdatedRestaurant(RESTAURANT_1));
        Restaurant expected = getUpdatedRestaurant(RESTAURANT_1);
        assertMatch(updated, expected);
    }
}