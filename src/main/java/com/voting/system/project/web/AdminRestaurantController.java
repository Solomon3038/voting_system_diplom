package com.voting.system.project.web;

import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.to.RestaurantTo;
import com.voting.system.project.util.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = AdminRestaurantController.ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    public static final String ADMIN_URL = "/admin";
    public static final String REST_URL = "/restaurants";

    private final RestaurantService restaurantService;
    private final ModelMapper mapper;

    public AdminRestaurantController(RestaurantService restaurantService, ModelMapper mapper) {
        this.restaurantService = restaurantService;
        this.mapper = mapper;
    }

    @GetMapping(REST_URL + "/{id}")
    public RestaurantTo getRestaurant(@PathVariable int id) {
        int userId = SecurityUtil.authUserId();
        log.info("User id is {} ", userId);
        return mapper.map(restaurantService.get(id), RestaurantTo.class);
    }
}
