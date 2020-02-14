package com.voting.system.project.service;

import com.voting.system.project.model.Dish;
import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.repository.RestaurantRepository;
import com.voting.system.project.to.RestaurantTo;
import com.voting.system.project.to.RestaurantWithMenusTo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.voting.system.project.util.RestaurantUtil.getFromTo;
import static com.voting.system.project.util.RestaurantUtil.getToFrom;
import static com.voting.system.project.util.ValidationUtil.checkNotExistWithId;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper mapper;

    public RestaurantTo get(int id) {
        Restaurant restaurant = restaurantRepository.findById(id);
        return mapper.map(checkNotExistWithId(restaurant, id), RestaurantTo.class);
    }

    public List<RestaurantTo> getAll() {
        final List<Restaurant> restaurants = restaurantRepository.findAll();
        final List<RestaurantTo> restaurantTos = new ArrayList<>();
        restaurants.forEach(restaurant -> restaurantTos.add(mapper.map(restaurant, RestaurantTo.class)));
        return restaurantTos;
    }

    //TODO add cache
    public List<RestaurantWithMenusTo> getAllWithMenusOnCurrentDate() {
        final List<Restaurant> restaurants = restaurantRepository.findAllWithMenusOnCurrentDate();
        final List<RestaurantWithMenusTo> restaurantTos = new ArrayList<>();
        restaurants.forEach(restaurant -> restaurantTos.add(getToFrom(restaurant, mapper)));
        return restaurantTos;
    }

    public Restaurant create(RestaurantTo restaurantTo) {
        Assert.notNull(restaurantTo, "restaurant must not be null");
        final Restaurant restaurant = getFromTo(restaurantTo);
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void update(RestaurantTo restaurantTo, int id) {
        Assert.notNull(restaurantTo, "restaurant must not be null");
        checkNotExistWithId(restaurantRepository.findById(restaurantTo.getId().intValue()), id);
        restaurantRepository.setValue(id, restaurantTo.getName(), restaurantTo.getAddress());
    }

    //TODO check transaction roll back if not valid menu or dishes
    public Restaurant createWithMenuAndDishes(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");

        final List<Menu> menus = restaurant.getMenus();
        Assert.isTrue(menus != null && menus.size() == 1, "restaurant must have one menu");

        final List<Dish> dishes = menus.iterator().next().getDishes();
        Assert.isTrue(!dishes.isEmpty(), "dishes must not be empty");
        return restaurantRepository.save(restaurant);
    }
}