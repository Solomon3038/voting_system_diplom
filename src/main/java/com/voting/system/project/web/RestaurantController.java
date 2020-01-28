package com.voting.system.project.web;

import com.voting.system.project.model.Restaurant;
import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.to.RestaurantWithMenusTo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.voting.system.project.web.AdminRestaurantController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public RestaurantWithMenusTo[] getAllWithMenusAndDishes() {
        final List<Restaurant> restaurants = restaurantService.getAllWithMenusOnCurrentDate();
        //https://stackoverflow.com/a/41743400
        return mapper.map(restaurants, RestaurantWithMenusTo[].class);
    }
}
