package com.voting.system.project.web;

import com.voting.system.project.model.Menu;
import com.voting.system.project.service.MenuService;
import com.voting.system.project.to.MenuTo;
import com.voting.system.project.to.MenuWithDishesTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.TestDataTo.*;
import static com.voting.system.project.util.MenuTestUtil.checkSave;
import static com.voting.system.project.util.MenuTestUtil.checkSaveWithDishes;
import static com.voting.system.project.util.MenuUtil.getFromTo;
import static com.voting.system.project.util.MenuUtil.getToFrom;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static com.voting.system.project.web.AdminRestaurantController.ADMIN_REST_URL;

@WithUserDetails(ADMIN_1_EMAIL)
class AdminMenuControllerTest extends AbstractControllerTest {

    public static final String ADMIN_MENU_URL_TEST = ADMIN_REST_URL + "/" + RESTAURANT_ID_1 + "/menus/";

    @Autowired
    private MenuService menuService;

    @Test
    void getMenus() throws Exception {
        final String menus = objectMapper.writeValueAsString(mapper.map(Arrays.asList(MENU_1_NOW, MENU_1), MenuWithDishesTo[].class));
        doGet(ADMIN_MENU_URL_TEST, menus);
    }

    @Test
    void getMenu() throws Exception {
        final String menu = objectMapper.writeValueAsString(getToFrom(MENU_1, mapper));
        doGet(ADMIN_MENU_URL_TEST + MENU_ID_1, menu);
    }

    @Test
    void createWithLocation() throws Exception {
        MenuTo newMenu = getNewMenuTo();
        String menu = objectMapper.writeValueAsString(newMenu);
        String result = doPost(menu, ADMIN_MENU_URL_TEST);
        MenuTo created = objectMapper.readValue(result, MenuTo.class);
        checkSave(getFromTo(created));
        newMenu.setId(created.getId());
        assertMatch(menuService.get(created.getId(), RESTAURANT_ID_1), newMenu);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void update() throws Exception {
        MenuTo updatedMenu = getUpdatedMenuTo(MENU_1);
        String menu = objectMapper.writeValueAsString(updatedMenu);
        doPut(menu, ADMIN_MENU_URL_TEST + MENU_ID_1);
        assertMatch(menuService.get(MENU_ID_1, RESTAURANT_ID_1), updatedMenu);
    }

    @Test
    void createWithLocationFull() throws Exception {
        MenuWithDishesTo newMenu = getNewMenuWithDishesTo();
        String menu = objectMapper.writeValueAsString(newMenu);
        String result = doPost(menu, ADMIN_MENU_URL_TEST + "/full");
        MenuWithDishesTo created = objectMapper.readValue(result, MenuWithDishesTo.class);
        checkSaveWithDishes(mapper.map(created, Menu.class));
        newMenu.setId(created.getId());
        assertMatch(menuService.get(created.getId(), RESTAURANT_ID_1), newMenu);
    }
}