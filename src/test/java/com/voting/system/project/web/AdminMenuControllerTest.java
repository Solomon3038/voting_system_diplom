package com.voting.system.project.web;

import com.voting.system.project.model.Menu;
import com.voting.system.project.service.DishService;
import com.voting.system.project.service.MenuService;
import com.voting.system.project.to.MenuTo;
import com.voting.system.project.to.MenuWithDishesTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.TestDataTo.*;
import static com.voting.system.project.util.MenuTestUtil.checkSave;
import static com.voting.system.project.util.MenuTestUtil.checkSaveWithDishes;
import static com.voting.system.project.util.MenuUtil.getToFrom;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static com.voting.system.project.web.AdminRestaurantController.ADMIN_REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(ADMIN_1_EMAIL)
class AdminMenuControllerTest extends AbstractControllerTest {

    public static final String ADMIN_MENU_URL_TEST = ADMIN_REST_URL + "/" + RESTAURANT_ID_1 + "/menus/";
    public static final String ADMIN_MENU_NOT_EXIST_URL_TEST = ADMIN_REST_URL + "/" + RESTAURANT_ID_NEXT + "/menus/";

    @Autowired
    private MenuService menuService;

    @Autowired
    private DishService dishService;

    @Test
    void getAll() throws Exception {
        final String menus = objectMapper.writeValueAsString(mapper.map(Arrays.asList(MENU_1_NOW, MENU_1), MenuWithDishesTo[].class));
        doGet(ADMIN_MENU_URL_TEST, menus);
    }

    @Test
    void get() throws Exception {
        final String menu = objectMapper.writeValueAsString(getToFrom(MENU_1, mapper));
        doGet(ADMIN_MENU_URL_TEST + MENU_ID_1, menu);
    }

    @Test
    void getNotExist() throws Exception {
        doGetNotExist(ADMIN_MENU_URL_TEST + NOT_EXIST_ID);
    }

    @Test
    void getNotOwn() throws Exception {
        doGetNotExist(ADMIN_MENU_URL_TEST + MENU_ID_2);
    }

    @Test
    void createWithLocation() throws Exception {
        final Menu newMenu = getNewMenu();
        final String menu = objectMapper.writeValueAsString(newMenu);
        final String result = doPost(menu, ADMIN_MENU_URL_TEST);
        final MenuWithDishesTo created = objectMapper.readValue(result, MenuWithDishesTo.class);
        checkSave(mapper.map(created, Menu.class));
        newMenu.setId(created.getId());
        assertMatch(menuService.get(created.getId(), RESTAURANT_ID_1), newMenu);
    }

    @Test
    void createWithLocationWithDishes() throws Exception {
        final MenuWithDishesTo newMenu = getNewMenuWithDishesTo();
        final String menu = objectMapper.writeValueAsString(newMenu);
        final String result = doPost(menu, ADMIN_MENU_URL_TEST);
        final MenuWithDishesTo created = objectMapper.readValue(result, MenuWithDishesTo.class);
        checkSaveWithDishes(mapper.map(created, Menu.class));
        newMenu.setId(created.getId());
        assertMatch(menuService.get(created.getId(), RESTAURANT_ID_1), newMenu);
    }

    @Test
    void createNotNew() throws Exception {
        final String menu = objectMapper.writeValueAsString(new MenuTo(MENU_ID_NEXT, LocalDate.now()));
        doPostErr(menu, ADMIN_MENU_URL_TEST, status().isUnprocessableEntity());
        Assertions.assertThrows(NotExistException.class, () -> menuService.get(MENU_ID_NEXT, RESTAURANT_ID_1));
    }

    @Test
    void createNotExist() throws Exception {
        final String menu = objectMapper.writeValueAsString(getNewMenuWithDishesTo());
        doPostErr(menu, ADMIN_MENU_NOT_EXIST_URL_TEST, status().isUnprocessableEntity());
        Assertions.assertThrows(NotExistException.class, () -> menuService.get(MENU_ID_NEXT, RESTAURANT_ID_NEXT));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicateData() throws Exception {
        doPostErr("{}", ADMIN_MENU_URL_TEST, status().isConflict());
        Assertions.assertThrows(NotExistException.class, () -> menuService.get(MENU_ID_NEXT, RESTAURANT_ID_1));
    }

    @Test
    void createInvalidDataError() throws Exception {
        final String menu = objectMapper.writeValueAsString(getInvalidNewMenuWithDishesTo());
        doPostErr(menu, ADMIN_MENU_URL_TEST, status().isUnprocessableEntity());
        Assertions.assertThrows(NotExistException.class, () -> menuService.get(MENU_ID_NEXT, RESTAURANT_ID_1));
        Assertions.assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_NEXT, MENU_ID_NEXT));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void update() throws Exception {
        final MenuTo updatedMenu = getUpdatedMenuTo(MENU_1);
        final String menu = objectMapper.writeValueAsString(updatedMenu);
        doPut(menu, ADMIN_MENU_URL_TEST + MENU_ID_1);
        assertMatch(menuService.get(MENU_ID_1, RESTAURANT_ID_1), updatedMenu);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateNotExist() throws Exception {
        final String menu = objectMapper.writeValueAsString(getUpdatedMenuTo(MENU_1));
        doPutErr(menu, ADMIN_MENU_NOT_EXIST_URL_TEST + MENU_ID_1, status().isUnprocessableEntity());
        assertMatch(menuService.get(MENU_ID_1, RESTAURANT_ID_1), MENU_1);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateNotOwn() throws Exception {
        final String menu = objectMapper.writeValueAsString(getUpdatedMenuTo(MENU_2));
        doPutErr(menu, ADMIN_MENU_URL_TEST + MENU_ID_2, status().isUnprocessableEntity());
        assertMatch(menuService.get(MENU_ID_2, RESTAURANT_ID_2), MENU_2);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicateData() throws Exception {
        final String menu = objectMapper.writeValueAsString(new MenuTo(MENU_ID_1, LocalDate.now()));
        doPutErr(menu, ADMIN_MENU_URL_TEST + MENU_ID_1, status().isConflict());
        assertMatch(menuService.get(MENU_ID_1, RESTAURANT_ID_1), MENU_1);
    }
}