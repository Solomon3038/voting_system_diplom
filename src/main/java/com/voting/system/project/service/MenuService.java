package com.voting.system.project.service;

import com.voting.system.project.model.Menu;
import com.voting.system.project.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.voting.system.project.util.ValidationUtil.checkNotExistWithId;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu getWithDishes(int id, int restaurantId) {
        Menu menu = menuRepository.findByIdWithDishes(id, restaurantId);
        return checkNotExistWithId(menu, id);
    }

    public List<Menu> getAllWithDishes(int restaurantId) {
        return menuRepository.findAllByRestaurantIdWithDishes(restaurantId);
    }

    public List<Menu> getAll(int restaurantId) {
        return menuRepository.findAllByRestaurantId(restaurantId);
    }

    public Menu createWithDishes(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        Assert.isTrue(!menu.getDishes().isEmpty(), "dishes must not be empty");
        return menuRepository.save(menu);
    }
}