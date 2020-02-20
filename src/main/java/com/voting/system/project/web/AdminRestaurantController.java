package com.voting.system.project.web;

import com.voting.system.project.model.Dish;
import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.to.RestaurantTo;
import com.voting.system.project.to.RestaurantWithMenusTo;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.voting.system.project.util.ControllerUtil.getResponseEntity;
import static com.voting.system.project.util.RestaurantUtil.getToFrom;
import static com.voting.system.project.util.ValidationUtil.assureIdConsistent;
import static com.voting.system.project.util.ValidationUtil.checkNew;

@Log4j2
@RestController
@RequestMapping(value = AdminRestaurantController.ADMIN_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    public static final String ADMIN_REST_URL = "/admin/restaurants";

    private final RestaurantService restaurantService;
    protected final ModelMapper mapper;

    public AdminRestaurantController(RestaurantService restaurantService, ModelMapper mapper, ModelMapper mapper1) {
        this.restaurantService = restaurantService;
        this.mapper = mapper1;
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("getAll restaurants");
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id) {
        log.info("get restaurant with id {}", id);
        return restaurantService.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantWithMenusTo> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        setNestedObjects(restaurant);
        final RestaurantWithMenusTo created = getToFrom(restaurantService.create(restaurant), mapper);
        return getResponseEntity(created, ADMIN_REST_URL + "/{id}", created.getId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        log.info("update {}", restaurantTo);
        assureIdConsistent(restaurantTo, id);
        restaurantService.update(restaurantTo, id);
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
