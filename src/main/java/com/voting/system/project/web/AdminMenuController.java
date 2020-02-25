package com.voting.system.project.web;

import com.voting.system.project.model.Dish;
import com.voting.system.project.model.Menu;
import com.voting.system.project.service.MenuService;
import com.voting.system.project.to.MenuTo;
import com.voting.system.project.to.MenuWithDishesTo;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
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
import static com.voting.system.project.util.MenuUtil.getToFrom;
import static com.voting.system.project.util.ValidationUtil.assureIdConsistent;
import static com.voting.system.project.util.ValidationUtil.checkNew;
import static com.voting.system.project.web.AdminRestaurantController.ADMIN_REST_URL;

@Log4j2
@RestController
@RequestMapping(value = AdminMenuController.ADMIN_MENU_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {

    public static final String ADMIN_MENU_URL = ADMIN_REST_URL + "/{restId}/menus";

    private final MenuService menuService;
    protected final ModelMapper mapper;

    public AdminMenuController(MenuService menuService, ModelMapper mapper) {
        this.menuService = menuService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<MenuWithDishesTo> getAll(@PathVariable int restId) {
        log.info("getMenus for restaurant with id {}", restId);
        return menuService.getAll(restId);
    }

    @GetMapping("/{id}")
    public MenuWithDishesTo get(@PathVariable int restId, @PathVariable int id) {
        log.info("getMenu with id {} for restaurant with id {}", id, restId);
        return menuService.get(id, restId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuWithDishesTo> createWithLocation(@Valid @RequestBody Menu menu, @PathVariable int restId) {
        log.info("create {}", menu);
        checkNew(menu);
        setNestedObjects(menu);
        final MenuWithDishesTo created = getToFrom(menuService.create(menu, restId), mapper);
        return getResponseEntity(created, ADMIN_MENU_URL + "/{id}", restId, created.getId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuTo menuTo, @PathVariable int restId, @PathVariable int id) {
        log.info("update {}", menuTo);
        assureIdConsistent(menuTo, id);
        menuService.update(menuTo, id, restId);
    }

    private void setNestedObjects(Menu menu) {
        final List<Dish> dishes = menu.getDishes();
        if (dishes != null && !dishes.isEmpty()) {
            dishes.forEach(dish -> dish.setMenu(menu));
        }
    }
}
