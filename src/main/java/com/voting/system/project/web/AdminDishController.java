package com.voting.system.project.web;

import com.voting.system.project.model.Dish;
import com.voting.system.project.service.DishService;
import com.voting.system.project.to.DishTo;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.voting.system.project.util.ValidationUtil.assureIdConsistent;
import static com.voting.system.project.util.ValidationUtil.checkNew;

@Log4j2
@RestController
@RequestMapping(value = AdminDishController.ADMIN_DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController extends AbstractAdminController {

    private final DishService dishService;

    public AdminDishController(DishService dishService, ModelMapper mapper) {
        super(mapper);
        this.dishService = dishService;
    }

    @GetMapping
    public List<DishTo> getAll(@PathVariable int menuId) {
        log.info("getDishes for menu with id {}", menuId);
        return dishService.getAll(menuId);
    }

    @GetMapping("/{id}")
    public DishTo get(@PathVariable int menuId, @PathVariable int id) {
        log.info("getDish with id {} for restaurant with id {}", id, menuId);
        return dishService.get(id, menuId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int menuId) {
        log.info("create {}", dish);
        checkNew(dish);
        final DishTo created = mapper.map(dishService.create(dish, menuId), DishTo.class);
        return getResponseEntity(created, ADMIN_DISH_URL + "/{id}", menuId, created.getId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int menuId, @PathVariable int id) {
        log.info("update {}", dish);
        assureIdConsistent(dish, id);
        dishService.update(dish, id, menuId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int menuId, @PathVariable int id) {
        log.info("delete dish {} in menu {}", id, menuId);
        dishService.delete(id, menuId);
    }
}
