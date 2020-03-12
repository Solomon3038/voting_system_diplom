package com.voting.system.project.service;

import com.voting.system.project.model.Dish;
import com.voting.system.project.repository.DishRepository;
import com.voting.system.project.repository.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.voting.system.project.util.ValidationUtil.checkNotExistWithId;
import static org.springframework.util.Assert.notNull;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<Dish> getAll(int restId) {
        return dishRepository.findAllByRestaurantId(restId);
    }

    public Dish get(int id, int restId) {
        final Dish dish = dishRepository.findDishByIdAndRestaurantId(id, restId);
        checkNotExistWithId(dish, id);
        return dish;
    }

    public Dish create(Dish dish, int restId) {
        notNull(dish, "dish must not be null");
        dish.setRestaurant(restaurantRepository.getOne(restId));
        return dishRepository.save(dish);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(Dish dish, int id, int restId) {
        notNull(dish, "dish must not be null");
        checkNotExistWithId(dishRepository.findDishByIdAndRestaurantId(id, restId), id);
        dish.setRestaurant(restaurantRepository.getOne(restId));
        dishRepository.save(dish);
    }
}