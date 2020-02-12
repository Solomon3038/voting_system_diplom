package com.voting.system.project.web;

import com.voting.system.project.model.Dish;
import com.voting.system.project.model.Menu;
import com.voting.system.project.service.MenuService;
import com.voting.system.project.to.MenuTo;
import com.voting.system.project.to.MenuWithDishesTo;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.voting.system.project.util.MenuUtil.getToFrom;
import static com.voting.system.project.util.ValidationUtil.checkNew;

@Log4j2
@RestController
@RequestMapping(value = AdminMenuController.ADMIN_MENU_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController extends AbstractAdminController {

    private final MenuService menuService;

    public AdminMenuController(MenuService menuService, ModelMapper mapper) {
        super(mapper);
        this.menuService = menuService;
    }

    @GetMapping
    public List<MenuWithDishesTo> getMenus(@PathVariable int restId) {
        log.info("getMenus for restaurant with id {}", restId);
        return menuService.getAll(restId);
    }

    @GetMapping("/{id}")
    public MenuWithDishesTo getMenu(@PathVariable int restId, @PathVariable int id) {
        log.info("getMenu with id {} for restaurant with id {}", id, restId);
        return menuService.get(id, restId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuTo> createWithLocation(@Valid @RequestBody Menu menu, @PathVariable int restId) {
        log.info("create {}", menu);
        checkNew(menu);
        final MenuTo created = mapper.map(menuService.create(menu, restId), MenuTo.class);
        return getResponseEntity(created, ADMIN_MENU_URL + "/{id}", restId, created.getId());
    }

    @PostMapping(value = "/full", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuWithDishesTo> createWithLocationFull(@Valid @RequestBody Menu menu, @PathVariable int restId) {
        log.info("create full {}", menu);
        checkNew(menu);
        setNestedObjects(menu);
        final MenuWithDishesTo created = getToFrom(menuService.createWithDishes(menu, restId), mapper);
        return getResponseEntity(created, ADMIN_MENU_URL + "/{id}", restId, created.getId());
    }

    private void setNestedObjects(Menu menu) {
        final List<Dish> dishes = menu.getDishes();
        if (dishes != null && !dishes.isEmpty()) {
            dishes.forEach(dish -> dish.setMenu(menu));
        }
    }
}
