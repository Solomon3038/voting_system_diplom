package com.voting.system.project.web;

import com.voting.system.project.service.MenuItemService;
import com.voting.system.project.to.MenuItemDishIdTo;
import com.voting.system.project.to.MenuItemDishNameTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.voting.system.project.TestDataHelper.*;
import static com.voting.system.project.util.MenuTestUtil.checkSave;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static com.voting.system.project.web.AdminRestaurantController.ADMIN_REST_URL;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(ADMIN_1_EMAIL)
class AdminMenuItemControllerTest extends AbstractControllerTest {

    public static final String ADMIN_MENU_URL_TEST = ADMIN_REST_URL + "/" + RESTAURANT_ID_1 + "/menu-items/";
    public static final String ADMIN_MENU_NOT_EXIST_URL_TEST = ADMIN_REST_URL + "/" + RESTAURANT_ID_NEXT + "/menu-items/";

    @Autowired
    private MenuItemService menuService;

    @Test
    void getAll() throws Exception {
        final List<MenuItemDishNameTo> tos = mapper.mapAsList(RESTAURANT_1_MENUS, MenuItemDishNameTo.class);
        final String menus = objectMapper.writeValueAsString(tos);
        doGet(ADMIN_MENU_URL_TEST, menus);
    }

    @Test
    void get() throws Exception {
        final String menu = objectMapper.writeValueAsString(mapper.map(MENU_1_1, MenuItemDishNameTo.class));
        doGet(ADMIN_MENU_URL_TEST + MENU_ID_1, menu);
    }

    @Test
    void getNotExist() throws Exception {
        doGetNotExist(ADMIN_MENU_URL_TEST + NOT_EXIST_ID);
    }

    @Test
    void getNotOwn() throws Exception {
        doGetNotExist(ADMIN_MENU_URL_TEST + MENU_ID_4);
    }

    @Test
    void createWithLocation() throws Exception {
        final MenuItemDishIdTo newMenu = getNewMenuItemDishIdTo();
        final String menu = objectMapper.writeValueAsString(newMenu);
        final String result = doPost(menu, ADMIN_MENU_URL_TEST);
        final MenuItemDishNameTo created = objectMapper.readValue(result, MenuItemDishNameTo.class);
        checkSave(created);
        newMenu.setId(created.getId());
        assertMatch(menuService.get(created.getId(), RESTAURANT_ID_1), newMenu);
    }

    @Test
    void createNotNew() throws Exception {
        final MenuItemDishIdTo updated = getUpdatedMenuItemDishIdTo(MENU_1_1);
        final String menu = objectMapper.writeValueAsString(updated);
        doPostErr(menu, ADMIN_MENU_URL_TEST, status().isUnprocessableEntity());
        assertThrows(NotExistException.class, () -> menuService.get(MENU_ID_NEXT, RESTAURANT_ID_1));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createNotExist() throws Exception {
        final String menu = objectMapper.writeValueAsString(getNewMenuItemDishIdTo());
        doPostErr(menu, ADMIN_MENU_NOT_EXIST_URL_TEST, status().isConflict());
        assertThrows(NotExistException.class, () -> menuService.get(MENU_ID_NEXT, RESTAURANT_ID_NEXT));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicateData() throws Exception {
        final MenuItemDishIdTo newMenu = getUpdatedMenuItemDishIdTo(MENU_1_1);
        newMenu.setId(null);
        newMenu.setDate(MENU_1_1.getDate());
        final String menu = objectMapper.writeValueAsString(newMenu);
        doPostErr(menu, ADMIN_MENU_URL_TEST, status().isConflict());
        assertThrows(NotExistException.class, () -> menuService.get(MENU_ID_NEXT, RESTAURANT_ID_1));
    }

    @Test
    void createInvalidDataError() throws Exception {
        final String menu = objectMapper.writeValueAsString(new MenuItemDishIdTo(LocalDate.of(2019, 8, 26), 1, 2));
        doPostErr(menu, ADMIN_MENU_URL_TEST, status().isUnprocessableEntity());
        assertThrows(NotExistException.class, () -> menuService.get(MENU_ID_NEXT, RESTAURANT_ID_1));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void update() throws Exception {
        final MenuItemDishIdTo updated = getUpdatedMenuItemDishIdTo(MENU_1_1);
        final String menu = objectMapper.writeValueAsString(updated);
        doPut(menu, ADMIN_MENU_URL_TEST + MENU_ID_1);
        assertMatch(menuService.get(MENU_ID_1, RESTAURANT_ID_1), updated);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateNotExist() throws Exception {
        final String menu = objectMapper.writeValueAsString(getUpdatedMenuItemDishIdTo(MENU_1_1));
        doPutErr(menu, ADMIN_MENU_NOT_EXIST_URL_TEST + MENU_ID_1, status().isUnprocessableEntity());
        assertMatch(menuService.get(MENU_ID_1, RESTAURANT_ID_1), MENU_1_1);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateNotOwn() throws Exception {
        final String menu = objectMapper.writeValueAsString(getUpdatedMenuItemDishIdTo(MENU_2_1));
        doPutErr(menu, ADMIN_MENU_URL_TEST + MENU_ID_4, status().isUnprocessableEntity());
        assertMatch(menuService.get(MENU_ID_4, RESTAURANT_ID_2), MENU_2_1);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicateData() throws Exception {
        MenuItemDishIdTo to = mapper.map(MENU_1_2, MenuItemDishIdTo.class);
        final String menu = objectMapper.writeValueAsString(to);
        doPutErr(menu, ADMIN_MENU_URL_TEST + MENU_ID_1, status().isConflict());
        assertMatch(menuService.get(MENU_ID_1, RESTAURANT_ID_1), MENU_1_1);
    }
}