package com.voting.system.project.web;

import com.voting.system.project.to.MenuTo;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.Arrays;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.web.AdminRestaurantController.ADMIN_REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/test-method.html#test-method-withuserdetails
class AdminMenuControllerTest extends AbstractControllerTest {

    public static final String ADMIN_MENU_URL_TEST = ADMIN_REST_URL + "/" + RESTAURANT_ID_1 + "/menus/";

    @Test
    @WithUserDetails(ADMIN_1_EMAIL)
    void getMenus() throws Exception {
        final String menus = objectMapper.writeValueAsString(mapper.map(Arrays.asList(MENU_1_NOW, MENU_1), MenuTo[].class));
        doGet(ADMIN_MENU_URL_TEST, menus);
    }

    @Test
    @WithUserDetails(ADMIN_1_EMAIL)
    void getMenu() throws Exception {
        final String menu = objectMapper.writeValueAsString(mapper.map(MENU_1, MenuTo.class));
        doGet(ADMIN_MENU_URL_TEST + MENU_ID_1, menu);
    }
}