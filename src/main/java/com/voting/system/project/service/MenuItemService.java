package com.voting.system.project.service;

import com.voting.system.project.model.MenuItem;
import com.voting.system.project.repository.DishRepository;
import com.voting.system.project.repository.MenuItemRepository;
import com.voting.system.project.to.MenuItemDishIdTo;
import com.voting.system.project.to.MenuItemDishNameTo;
import com.voting.system.project.util.mapper.OrikaMapper;
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
    private final OrikaMapper mapper;

    public MenuItemService(MenuItemRepository menuItemRepository,
                           DishRepository dishRepository,
                           OrikaMapper mapper) {
        this.menuItemRepository = menuItemRepository;
        this.dishRepository = dishRepository;
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
    public MenuItemDishNameTo create(MenuItemDishIdTo menuItemDishIdTo) {
        return createMenuItem(menuItemDishIdTo);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(MenuItemDishIdTo menuItemDishIdTo, int id, int restaurantId) {
        checkNotExistWithId(menuItemRepository.findByIdAndRestaurantId(id, restaurantId), id);
        createMenuItem(menuItemDishIdTo);
    }

    private MenuItemDishNameTo createMenuItem(MenuItemDishIdTo menuItemDishIdTo) {
        notNull(menuItemDishIdTo, "menuItemDishIdTo must not be null");
        final MenuItem menuItem = mapper.map(menuItemDishIdTo, MenuItem.class);
        menuItem.setDish(dishRepository.getOne(menuItemDishIdTo.getDishId()));
        final MenuItem saved = menuItemRepository.save(menuItem);
        return mapper.map(saved, MenuItemDishNameTo.class);
    }
}