package com.voting.system.project.service;

import com.voting.system.project.model.Dish;
import com.voting.system.project.repository.DishRepository;
import com.voting.system.project.repository.MenuRepository;
import com.voting.system.project.to.DishTo;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.voting.system.project.util.ValidationUtil.checkNotExistWithId;
import static org.springframework.util.Assert.notNull;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final MenuRepository menuRepository;
    private final ModelMapper mapper;

    public DishService(DishRepository dishRepository, MenuRepository menuRepository, ModelMapper mapper) {
        this.dishRepository = dishRepository;
        this.menuRepository = menuRepository;
        this.mapper = mapper;
    }

    public List<DishTo> getAll(int menuId) {
        final List<Dish> dishes = dishRepository.findAllByMenuIdOrderByNameAsc(menuId);
        final List<DishTo> tos = new ArrayList<>();
        dishes.forEach(dish -> tos.add(mapper.map(dish, DishTo.class)));
        return tos;
    }

    public DishTo get(int id, int menuId) {
        final Dish dish = dishRepository.findDishByIdAndMenuId(id, menuId);
        checkNotExistWithId(dish, id);
        return mapper.map(dish, DishTo.class);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public Dish create(Dish dish, int menuId) {
        notNull(dish, "dish must not be null");
        dish.setMenu(menuRepository.getOne(menuId));
        return dishRepository.save(dish);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(Dish dish, int id, int menuId) {
        notNull(dish, "dish must not be null");
        checkNotExistWithId(dishRepository.findDishByIdAndMenuId(id, menuId), id);
        dish.setMenu(menuRepository.getOne(menuId));
        dishRepository.save(dish);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id, int menuId) {
        checkNotExistWithId(dishRepository.deleteByIdAndMenuId(id, menuId) == 1, id);
    }
}