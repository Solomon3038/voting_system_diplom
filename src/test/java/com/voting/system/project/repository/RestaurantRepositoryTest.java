package com.voting.system.project.repository;

import com.voting.system.project.model.Restaurant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.RestaurantTestUtil.*;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RestaurantRepositoryTest extends AbstractRepositoryTest {

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
        checkEntityFieldLoadingType(Restaurant.class, "menus", false);
        checkAllWithMenusOnCurrentDate(actual);
    }

    @Test
    void saveWithMenusAndDishes() {
        Restaurant saved = restaurantRepository.save(getNewRestaurantWithMenuAndDishes());
        checkSaveWithMenusAndDishes(saved);
    }

    @Test
    void save() {
        Restaurant saved = restaurantRepository.save(getNewRestaurant());
        checkSave(saved);
    }

    @Test
    void update() {
        final Restaurant restaurant = getUpdatedRestaurant(RESTAURANT_1);
        int rows = restaurantRepository.setValue(RESTAURANT_ID_1, restaurant.getName(), restaurant.getAddress());
        assertEquals(1, rows);
        Restaurant updated = restaurantRepository.findById(RESTAURANT_ID_1);
        checkUpdate(updated);
    }

    @Test
    void updateNotExist() {
        int rows = restaurantRepository.setValue(NOT_EXIST_ID, "not exist name", "not exist address");
        assertEquals(0, rows);
        Restaurant updated = restaurantRepository.findById(NOT_EXIST_ID);
        Assertions.assertNull(updated);
    }
}