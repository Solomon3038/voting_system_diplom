package com.voting.system.project.service;

import com.voting.system.project.mapper.OrikaMapper;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.repository.RestaurantRepository;
import com.voting.system.project.to.RestaurantTo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.voting.system.project.util.ValidationUtil.checkNotExistWithId;
import static org.springframework.util.Assert.notNull;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final OrikaMapper mapper;

    public RestaurantService(RestaurantRepository restaurantRepository, OrikaMapper mapper) {
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
    }

    public Restaurant get(int id) {
        final Restaurant restaurant = restaurantRepository.findById(id);
        return checkNotExistWithId(restaurant, id);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @Cacheable("restaurants")
    public List<RestaurantTo> getAllWithMenusOnCurrentDate() {
        List<Restaurant> restaurants = restaurantRepository.findAllWithMenusOnCurrentDate();
        return mapper.mapAsList(restaurants, RestaurantTo.class);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public Restaurant create(Restaurant restaurant) {
        notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(Restaurant restaurant, int id) {
        notNull(restaurant, "restaurant must not be null");
        checkNotExistWithId(restaurantRepository.findById(restaurant.getId().intValue()), id);
        restaurantRepository.save(restaurant);
    }
}