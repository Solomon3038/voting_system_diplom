package com.voting.system.project.service;

import com.voting.system.project.model.Dish;
import com.voting.system.project.model.Menu;
import com.voting.system.project.repository.DishRepository;
import com.voting.system.project.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.voting.system.project.util.ValidationUtil.checkNotExistWithId;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private MenuRepository menuRepository;

    public List<Dish> getAll(int menuId) {
        return dishRepository.findAllByMenuIdOrderByNameAsc(menuId);
    }

    public Dish get(int id, int menuId) {
        Dish dish = dishRepository.findDishByIdAndMenuId(id, menuId);
        return checkNotExistWithId(dish, id);
    }

    @Transactional
    public Dish save(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        Menu menu = checkNotExistWithId(menuRepository.findById(menuId).orElse(null), menuId);
        dish.setMenu(menu);
        return dishRepository.save(dish);
    }

    @Transactional
    public void update(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        checkNotExistWithId(dishRepository.findDishByIdAndMenuId(dish.getId(), menuId), dish.getId());
        dish.setMenu(menuRepository.getOne(menuId));
        dishRepository.save(dish);
    }

    public void delete(int id, int menuId) {
        checkNotExistWithId(dishRepository.deleteByIdAndMenuId(id, menuId) == 1, id);
    }
}