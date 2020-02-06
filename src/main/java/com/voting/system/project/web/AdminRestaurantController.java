package com.voting.system.project.web;

import com.voting.system.project.service.MenuService;
import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.to.MenuTo;
import com.voting.system.project.to.RestaurantTo;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping(value = AdminRestaurantController.ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    public static final String ADMIN_URL = "/admin";
    public static final String REST_URL = "/restaurants";
    public static final String MENU_URL = "/menus";

    private final RestaurantService restaurantService;
    private final MenuService menuService;

    public AdminRestaurantController(RestaurantService restaurantService, MenuService menuService) {
        this.restaurantService = restaurantService;
        this.menuService = menuService;
    }

    @GetMapping(REST_URL)
    public List<RestaurantTo> getAllRestaurants() {
        log.info("getAll restaurants");
        return restaurantService.getAll();
    }

    @GetMapping(REST_URL + "/{id}")
    public RestaurantTo getRestaurant(@PathVariable int id) {
        log.info("get restaurant with id {}", id);
        return restaurantService.get(id);
    }

    @GetMapping(REST_URL + "/{id}" + MENU_URL)
    public List<MenuTo> getMenus(@PathVariable int id) {
        log.info("getMenus for restaurant with id {}", id);
        return menuService.getAll(id);
    }

    @GetMapping(REST_URL + "/{restId}" + MENU_URL+ "/{id}")
    public MenuTo getMenu(@PathVariable int restId, @PathVariable int id) {
        log.info("getMenu with id {} for restaurant with id {}", id, restId);
        return menuService.get(id, restId);
    }
}
