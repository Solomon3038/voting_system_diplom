package com.voting.system.project.service;

import com.voting.system.project.model.Menu;
import com.voting.system.project.repository.MenuRepository;
import com.voting.system.project.repository.RestaurantRepository;
import com.voting.system.project.to.MenuTo;
import com.voting.system.project.to.MenuWithDishesTo;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.voting.system.project.util.MenuUtil.getFromTo;
import static com.voting.system.project.util.MenuUtil.getToFrom;
import static com.voting.system.project.util.ValidationUtil.checkNotExistWithId;
import static org.springframework.util.Assert.notNull;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper mapper;

    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository, ModelMapper mapper) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
    }

    public MenuWithDishesTo get(int id, int restaurantId) {
        final Menu menu = menuRepository.findByIdAndRestaurantId(id, restaurantId);
        checkNotExistWithId(menu, id);
        return getToFrom(menu, mapper);
    }

    public List<MenuWithDishesTo> getAll(int restaurantId) {
        final List<Menu> menus = menuRepository.findAllByRestaurantIdOrderByRegisteredDesc(restaurantId);
        final List<MenuWithDishesTo> tos = new ArrayList<>();
        menus.forEach(menu -> tos.add(getToFrom(menu, mapper)));
        return tos;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        notNull(menu, "menu must not be null");
        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        return menuRepository.save(menu);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(MenuTo menuTo, int id, int restaurantId) {
        notNull(menuTo, "menu must not be null");
        checkNotExistWithId(menuRepository.findByIdAndRestaurantId(id, restaurantId), id);
        final Menu menu = getFromTo(menuTo);
        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        menuRepository.save(menu);
    }
}