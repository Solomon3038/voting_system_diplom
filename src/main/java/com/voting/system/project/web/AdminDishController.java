package com.voting.system.project.web;

import com.voting.system.project.model.Dish;
import com.voting.system.project.service.DishService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.voting.system.project.util.ControllerUtil.getResponseEntity;
import static com.voting.system.project.util.ValidationUtil.assureIdConsistent;
import static com.voting.system.project.util.ValidationUtil.checkNew;
import static com.voting.system.project.web.AdminRestaurantController.ADMIN_REST_URL;

@Log4j2
@RestController
@RequestMapping(value = AdminDishController.ADMIN_DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {

    public static final String ADMIN_DISH_URL = ADMIN_REST_URL + "/{restId}/dishes";

    private final DishService dishService;

    public AdminDishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public List<Dish> getAll(@PathVariable int restId) {
        log.info("getDishes");
        return dishService.getAll(restId);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id,
                    @PathVariable int restId) {
        log.info("getDish with id {} for restaurant with id {} ", id, restId);
        return dishService.get(id, restId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish,
                                                   @PathVariable int restId) {
        log.info("create {} for restaurant with id {} ", dish, restId);
        checkNew(dish);
        final Dish created = dishService.create(dish, restId);
        return getResponseEntity(created, ADMIN_DISH_URL + "/{id}", restId, created.getId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish,
                       @PathVariable int id,
                       @PathVariable int restId) {
        log.info("update {} for restaurant with id {} ", dish, restId);
        assureIdConsistent(dish, id);
        dishService.update(dish, id, restId);
    }
}
