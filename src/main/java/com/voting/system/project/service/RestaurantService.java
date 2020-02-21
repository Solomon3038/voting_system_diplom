package com.voting.system.project.service;

import com.voting.system.project.model.Restaurant;
import com.voting.system.project.repository.RestaurantRepository;
import com.voting.system.project.to.RestaurantTo;
import com.voting.system.project.to.RestaurantWithMenusTo;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    private final RestaurantRepository restaurantRepository;
    private final ModelMapper mapper;

    public RestaurantService(RestaurantRepository restaurantRepository, ModelMapper mapper) {
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
    }

    public RestaurantTo get(int id) {
        final Restaurant restaurant = restaurantRepository.findById(id);
        return mapper.map(checkNotExistWithId(restaurant, id), RestaurantTo.class);
    }

    public List<RestaurantTo> getAll() {
        final List<Restaurant> restaurants = restaurantRepository.findAll();
        final List<RestaurantTo> restaurantTos = new ArrayList<>();
        restaurants.forEach(restaurant -> restaurantTos.add(mapper.map(restaurant, RestaurantTo.class)));
        return restaurantTos;
    }

    @Cacheable("restaurants")
    public List<RestaurantWithMenusTo> getAllWithMenusOnCurrentDate() {
        final List<Restaurant> restaurants = restaurantRepository.findAllWithMenusOnCurrentDate();
        final List<RestaurantWithMenusTo> restaurantTos = new ArrayList<>();
        restaurants.forEach(restaurant -> restaurantTos.add(getToFrom(restaurant, mapper)));
        return restaurantTos;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(RestaurantTo restaurantTo, int id) {
        Assert.notNull(restaurantTo, "restaurant must not be null");
        checkNotExistWithId(restaurantRepository.findById(restaurantTo.getId().intValue()), id);
        Restaurant restaurant = getFromTo(restaurantTo);
        restaurantRepository.save(restaurant);
    }
}