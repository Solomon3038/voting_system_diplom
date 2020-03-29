package com.voting.system.project.web;

import com.voting.system.project.service.MenuItemService;
import com.voting.system.project.to.MenuItemDishIdTo;
import com.voting.system.project.to.MenuItemDishNameTo;
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
@RequestMapping(value = AdminMenuItemController.ADMIN_MENU_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuItemController {

    public static final String ADMIN_MENU_URL = ADMIN_REST_URL + "/{restId}/menu-items";

    private final MenuItemService menuItemService;

    public AdminMenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public List<MenuItemDishNameTo> getAll(@PathVariable int restId) {
        log.info("getMenuItems for restaurant with id {}", restId);
        return menuItemService.getAll(restId);
    }

    @GetMapping("/{id}")
    public MenuItemDishNameTo get(@PathVariable int id,
                                  @PathVariable int restId) {
        log.info("getMenuItem with id {} for restaurant with id {}", id, restId);
        return menuItemService.get(id, restId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItemDishNameTo> createWithLocation(@Valid @RequestBody MenuItemDishIdTo menuItemDishIdTo,
                                                                 @PathVariable int restId) {
        log.info("create {}", menuItemDishIdTo);
        checkNew(menuItemDishIdTo);
        final MenuItemDishNameTo created = menuItemService.create(menuItemDishIdTo, restId);
        return getResponseEntity(created, ADMIN_MENU_URL + "/{id}", restId, created.getId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuItemDishIdTo menuItemDishIdTo,
                       @PathVariable int id,
                       @PathVariable int restId) {
        log.info("update {}", menuItemDishIdTo);
        assureIdConsistent(menuItemDishIdTo, id);
        menuItemService.update(menuItemDishIdTo, id, restId);
    }
}
