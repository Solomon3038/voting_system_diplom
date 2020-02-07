package com.voting.system.project.web;

import com.voting.system.project.service.MenuService;
import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.to.MenuTo;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.voting.system.project.web.AdminRestaurantController.ADMIN_REST_URL;

@Log4j2
@RestController
@RequestMapping(value = AdminMenuController.ADMIN_MENU_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {

    public static final String ADMIN_MENU_URL = ADMIN_REST_URL + "/{restId}/menus";

    private final RestaurantService restaurantService;
    private final MenuService menuService;

    public AdminMenuController(RestaurantService restaurantService, MenuService menuService) {
        this.restaurantService = restaurantService;
        this.menuService = menuService;
    }

    @GetMapping
    public List<MenuTo> getMenus(@PathVariable int restId) {
        log.info("getMenus for restaurant with id {}", restId);
        return menuService.getAll(restId);
    }

    @GetMapping("/{id}")
    public MenuTo getMenu(@PathVariable int restId, @PathVariable int id) {
        log.info("getMenu with id {} for restaurant with id {}", id, restId);
        return menuService.get(id, restId);
    }
}
