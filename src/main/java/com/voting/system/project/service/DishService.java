package com.voting.system.project.service;

import com.voting.system.project.model.Dish;
import com.voting.system.project.repository.DishRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.voting.system.project.util.ValidationUtil.checkNotExistWithId;
import static org.springframework.util.Assert.notNull;

@Service
public class DishService {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getAll() {
        return dishRepository.getAllByOrderByNameAsc();
    }

    public Dish get(int id) {
        final Dish dish = dishRepository.findDishById(id);
        checkNotExistWithId(dish, id);
        return dish;
    }

    public Dish create(Dish dish) {
        notNull(dish, "dish must not be null");
        return dishRepository.save(dish);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(Dish dish, int id) {
        notNull(dish, "dish must not be null");
        checkNotExistWithId(dishRepository.findDishById(id), id);
        dishRepository.save(dish);
    }
}