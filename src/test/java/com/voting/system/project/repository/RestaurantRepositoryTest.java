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
        Restaurant restaurant = restaurantRepository.findById(RESTAURANT_ID_1);
        assertMatch(restaurant, RESTAURANT_1);
        checkEntityFieldLoadingType(Restaurant.class, "menus", true);
    }

    @Test
    void findByIdNotExist() {
        Restaurant restaurantNotExist = restaurantRepository.findById(NOT_EXIST_ID);
        Assertions.assertNull(restaurantNotExist);
    }

    @Test
    void findAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        assertMatch(restaurants, RESTAURANTS);
        checkEntityFieldLoadingType(Restaurant.class, "menus", true);
    }

    @Test
    void findAllWithMenusOnCurrentDate() {
        List<Restaurant> restaurantsWithMenu = restaurantRepository.findAllWithMenusOnCurrentDate();
        assertMatch(restaurantsWithMenu, RESTAURANTS_WITH_MENU_ON_CURRENT_DATE);
        checkEntityFieldLoadingType(Restaurant.class, "menus", false);
        List<Menu> menus = restaurantsWithMenu.stream()
                .map(Restaurant::getMenus)
                .flatMap(Set::stream)
                .sorted(Comparator.comparing(Menu::getId))
                .collect(Collectors.toList());
        assertMatch(menus, MENUS_NOW);
    }

    @Test
    void saveWithMenusAndDishes() {
        Restaurant saved = restaurantRepository.save(getNewRestaurantWithMenuAndDishes());
        Restaurant newRestaurant = getNewRestaurantWithMenuAndDishes();
        int id = saved.getId();
        newRestaurant.setId(id);
        int menuId = saved.getMenus().iterator().next().getId();
        newRestaurant.getMenus().iterator().next().setId(menuId);
        assertMatch(newRestaurant, saved);
        assertMatch(newRestaurant.getMenus(), saved.getMenus());
    }

    @Test
    void save() {
        Restaurant saved = restaurantRepository.save(getNewRestaurant());
        Restaurant newRestaurant = getNewRestaurant();
        newRestaurant.setId(saved.getId());
        assertMatch(newRestaurant, saved);
    }
}