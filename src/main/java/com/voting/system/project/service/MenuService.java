package com.voting.system.project.service;

import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.repository.MenuRepository;
import com.voting.system.project.repository.RestaurantRepository;
import com.voting.system.project.to.MenuWithDishesTo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.voting.system.project.util.MenuUtil.getToFrom;
import static com.voting.system.project.util.ValidationUtil.checkNotExistWithId;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper mapper;

    public MenuWithDishesTo get(int id, int restaurantId) {
        Menu menu = menuRepository.findByIdAndRestaurantId(id, restaurantId);
        checkNotExistWithId(menu, id);
        return mapper.map(menu, MenuWithDishesTo.class);
    }

    public List<MenuWithDishesTo> getAll(int restaurantId) {
        final List<Menu> menus = menuRepository.findAllByRestaurantIdOrderByRegisteredDesc(restaurantId);
        final List<MenuWithDishesTo> tos = new ArrayList<>();
        menus.forEach(menu -> tos.add(getToFrom(menu, mapper)));
        return tos;
    }

    //TODO check transaction roll back dishes not valid
    @Transactional
    public Menu createWithDishes(Menu menu, int restaurantId) {
        checkAndSetRestaurant(menu, restaurantId);
        Assert.isTrue(!menu.getDishes().isEmpty(), "dishes must not be empty");
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        checkAndSetRestaurant(menu, restaurantId);
        return menuRepository.save(menu);
    }

    @Transactional
    public void update(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        checkNotExistWithId(menuRepository.findByIdAndRestaurantId(menu.getId(), restaurantId), menu.getId());
        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        menuRepository.save(menu);
    }

    private void checkAndSetRestaurant(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        Restaurant restaurant = checkNotExistWithId(restaurantRepository.findById(restaurantId), restaurantId);
        menu.setRestaurant(restaurant);
    }
}