package com.voting.system.project.repository;

import com.voting.system.project.ProjectApplicationTests;
import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.voting.system.project.TestData.MENUS_NOW;
import static com.voting.system.project.TestData.RESTAURANTS_NOW;
import static com.voting.system.project.TestMatcherUtil.*;

class RestaurantRepositoryTest extends ProjectApplicationTests {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void findById() {
    }

    @Test
    void findByIdNotExist() {
    }

    @Test
    void findAll() {

    }

    @Test
    void findAllWithMenusOnDate() {
        List<Restaurant> restaurantsWithMenu = restaurantRepository.findAllWithMenusOnDate();
        assertMatch(restaurantsWithMenu, RESTAURANTS_NOW);
        List<Menu> menus = restaurantsWithMenu.stream()
                .map(Restaurant::getMenus)
                .flatMap(Set::stream)
                .sorted(Comparator.comparing(Menu::getId))
                .collect(Collectors.toList());
        assertMatch(menus, MENUS_NOW);
    }
}