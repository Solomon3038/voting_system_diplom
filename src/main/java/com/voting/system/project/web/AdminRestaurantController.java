package com.voting.system.project.web;

import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.to.RestaurantTo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = AdminRestaurantController.ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    public static final String ADMIN_URL = "/admin";
    public static final String REST_URL = "/restaurants";

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(REST_URL + "/{id}")
    public RestaurantTo get(@PathVariable int id) {
        return mapper.map(restaurantService.get(id), RestaurantTo.class);
    }
}
