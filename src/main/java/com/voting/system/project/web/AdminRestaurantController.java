package com.voting.system.project.web;

import com.voting.system.project.model.Dish;
import com.voting.system.project.model.HasId;
import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.to.RestaurantTo;
import com.voting.system.project.to.RestaurantWithMenusTo;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.voting.system.project.util.RestaurantUtil.getToFrom;
import static com.voting.system.project.util.ValidationUtil.checkNew;

@Log4j2
@RestController
@RequestMapping(value = AdminRestaurantController.ADMIN_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    public static final String ADMIN_REST_URL = "/admin/restaurants";

    private final RestaurantService restaurantService;
    private final ModelMapper mapper;

    public AdminRestaurantController(RestaurantService restaurantService, ModelMapper mapper) {
        this.restaurantService = restaurantService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<RestaurantTo> getAllRestaurants() {
        log.info("getAll restaurants");
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    public RestaurantTo getRestaurant(@PathVariable int id) {
        log.info("get restaurant with id {}", id);
        return restaurantService.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return getResponseEntity(restaurantService.create(restaurant));
    }

    @PostMapping(value = "/full", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantWithMenusTo> createWithLocationFull(@Valid @RequestBody Restaurant restaurant) {
        log.info("create full {}", restaurant);
        checkNew(restaurant);
        setNestedObjects(restaurant);
        return getResponseEntity(getToFrom(restaurantService.createWithMenuAndDishes(restaurant), mapper));
    }

    private <T extends HasId> ResponseEntity<T> getResponseEntity(T created) {
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    private void setNestedObjects(Restaurant restaurant) {
        List<Menu> menus = restaurant.getMenus();
        if (menus != null && !menus.isEmpty()) {
            menus.forEach(menu -> {
                menu.setRestaurant(restaurant);
                final List<Dish> dishes = menu.getDishes();
                if (dishes != null && !dishes.isEmpty()) {
                    dishes.forEach(dish -> dish.setMenu(menu));
                }
            });
        }
    }
}
