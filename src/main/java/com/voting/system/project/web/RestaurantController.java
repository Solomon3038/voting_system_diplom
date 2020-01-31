package com.voting.system.project.web;

import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.to.RestaurantWithMenusTo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.voting.system.project.web.AdminRestaurantController.REST_URL;

@Log4j2
@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public RestaurantWithMenusTo[] getAllWithMenusAndDishes() {
        return restaurantService.getAllWithMenusOnCurrentDate();
    }
}
