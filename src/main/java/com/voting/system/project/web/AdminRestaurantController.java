package com.voting.system.project.web;

import com.voting.system.project.config.UserPrincipal;
import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.to.RestaurantTo;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Log4j2
@RestController
@RequestMapping(value = AdminRestaurantController.ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    public static final String ADMIN_URL = "/admin";
    public static final String REST_URL = "/restaurants";

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ModelMapper mapper;

    //https://blog.codeleak.pl/2016/09/injecting-authenticated-user-into.html
    @GetMapping(REST_URL + "/{id}")
    public RestaurantTo get(@PathVariable int id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("User id is {}", userPrincipal.getId());
        return mapper.map(restaurantService.get(id), RestaurantTo.class);
    }
}
