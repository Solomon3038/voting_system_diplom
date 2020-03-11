package com.voting.system.project.service;

import com.voting.system.project.mapper.OrikaMapper;
import com.voting.system.project.model.MenuItem;
import com.voting.system.project.repository.DishRepository;
import com.voting.system.project.repository.MenuItemRepository;
import com.voting.system.project.repository.RestaurantRepository;
import com.voting.system.project.to.MenuItemDishIdTo;
import com.voting.system.project.to.MenuItemDishNameTo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.voting.system.project.util.ValidationUtil.checkNotExistWithId;
import static org.springframework.util.Assert.notNull;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrikaMapper mapper;

    public MenuItemService(MenuItemRepository menuItemRepository,
                           DishRepository dishRepository,
                           RestaurantRepository restaurantRepository,
                           OrikaMapper mapper) {
        this.menuItemRepository = menuItemRepository;
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
    }

    public List<MenuItemDishNameTo> getAll(int restaurantId) {
        return mapper.mapAsList(menuItemRepository.findAllByRestaurantId(restaurantId), MenuItemDishNameTo.class);
    }

    public MenuItemDishNameTo get(int id, int restaurantId) {
        final MenuItem menuItem = menuItemRepository.findByIdAndRestaurantId(id, restaurantId);
        checkNotExistWithId(menuItem, id);
        return mapper.map(menuItem, MenuItemDishNameTo.class);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public MenuItemDishNameTo create(MenuItemDishIdTo menuItemDishIdTo, int restaurantId) {
        return getMenuItem(menuItemDishIdTo, restaurantId);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(MenuItemDishIdTo menuItemDishIdTo, int id, int restaurantId) {
        checkNotExistWithId(menuItemRepository.findByIdAndRestaurantId(id, restaurantId), id);
        getMenuItem(menuItemDishIdTo, restaurantId);
    }

    private MenuItemDishNameTo getMenuItem(MenuItemDishIdTo menuItemDishIdTo, int restaurantId) {
        notNull(menuItemDishIdTo, "menuItemTo must not be null");
        MenuItem menuItem = mapper.map(menuItemDishIdTo, MenuItem.class);
        menuItem.setDish(dishRepository.getOne(menuItemDishIdTo.getDishId()));
        menuItem.setRestaurant(restaurantRepository.getOne(restaurantId));
        MenuItem saved = menuItemRepository.save(menuItem);
        return mapper.map(saved, MenuItemDishNameTo.class);
    }
}