package com.voting.system.project.web;

import com.voting.system.project.model.Restaurant;
import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.to.RestaurantTo;
import jdk.jfr.ContentType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {
    public static final String RESTAURANTS = "/restaurants";

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(RESTAURANTS + "/{id}")
    public RestaurantTo get(@PathVariable int id) {
        final RestaurantTo restaurantTo = mapper.map(restaurantService.get(id), RestaurantTo.class);
        return restaurantTo;
    }
}
