package com.voting.system.project.repository;

import com.voting.system.project.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.system.project.TestDataHelper.NOT_EXIST_ID;
import static com.voting.system.project.TestDataHelper.RESTAURANTS;
import static com.voting.system.project.TestDataHelper.RESTAURANT_1;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_1;
import static com.voting.system.project.TestDataHelper.getNewRestaurant;
import static com.voting.system.project.TestDataHelper.getNewRestaurantWithMenuAndDishes;
import static com.voting.system.project.TestDataHelper.getUpdatedRestaurant;
import static com.voting.system.project.util.RestaurantTestUtil.checkAllWithMenusOnCurrentDate;
import static com.voting.system.project.util.RestaurantTestUtil.checkSave;
import static com.voting.system.project.util.RestaurantTestUtil.checkSaveWithMenuAndDishes;
import static com.voting.system.project.util.RestaurantTestUtil.checkUpdate;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.jupiter.api.Assertions.assertNull;

class RestaurantRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void findById() {
        final Restaurant actual = restaurantRepository.findById(RESTAURANT_ID_1);
        assertMatch(actual, RESTAURANT_1);
        checkEntityFieldLoadingType(Restaurant.class, "menus", true);
    }

    @Test
    void findByIdNotExist() {
        final Restaurant actual = restaurantRepository.findById(NOT_EXIST_ID);
        assertNull(actual);
    }

    @Test
    void findAll() {
        final List<Restaurant> actual = restaurantRepository.findAll();
        assertMatch(actual, RESTAURANTS);
        checkEntityFieldLoadingType(Restaurant.class, "menus", true);
    }

    @Test
    void findAllWithMenusOnCurrentDate() {
        final List<Restaurant> actual = restaurantRepository.findAllWithMenusOnCurrentDate();
        checkEntityFieldLoadingType(Restaurant.class, "menus", false);
        checkAllWithMenusOnCurrentDate(actual);
    }

    @Test
    void saveWithMenusAndDishes() {
        final Restaurant saved = restaurantRepository.save(getNewRestaurantWithMenuAndDishes());
        checkSaveWithMenuAndDishes(saved);
    }

    @Test
    void save() {
        final Restaurant saved = restaurantRepository.save(getNewRestaurant());
        checkSave(saved);
    }

    @Test
    void update() {
        final Restaurant updated = restaurantRepository.save(getUpdatedRestaurant(RESTAURANT_1));
        checkUpdate(updated);
    }
}