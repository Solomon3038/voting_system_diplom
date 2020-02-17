package com.voting.system.project.web;

import com.voting.system.project.service.DishService;
import com.voting.system.project.to.DishTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.TestDataTo.getNewDishTo;
import static com.voting.system.project.TestDataTo.getUpdatedDishTo;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WithUserDetails(ADMIN_1_EMAIL)
class AdminDishControllerTest extends AbstractControllerTest {

    public static final String ADMIN_DISH_URL_TEST = "/admin/menus/" + MENU_ID_1 + "/dishes/";

    @Autowired
    private DishService dishService;

    @Test
    void getDishes() throws Exception {
        final String dishes = objectMapper.writeValueAsString(mapper.map(Arrays.asList(DISH_1_3, DISH_1_2, DISH_1_1), DishTo[].class));
        doGet(ADMIN_DISH_URL_TEST, dishes);
    }

    @Test
    void getDish() throws Exception {
        final String dish = objectMapper.writeValueAsString(mapper.map(DISH_1_1, DishTo.class));
        doGet(ADMIN_DISH_URL_TEST + DISH_ID_1, dish);
    }

    @Test
    void createWithLocation() throws Exception {
        final DishTo newDish = getNewDishTo();
        final String dish = objectMapper.writeValueAsString(newDish);
        final String result = doPost(dish, ADMIN_DISH_URL_TEST);
        final DishTo created = objectMapper.readValue(result, DishTo.class);
        newDish.setId(created.getId());
        assertMatch(dishService.get(created.getId(), MENU_ID_1), newDish);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void update() throws Exception {
        final DishTo updatedDish = getUpdatedDishTo(DISH_1_1);
        final String dish = objectMapper.writeValueAsString(updatedDish);
        doPut(dish, ADMIN_DISH_URL_TEST + DISH_ID_1);
        assertMatch(dishService.get(DISH_ID_1, MENU_ID_1), updatedDish);
    }

    @Test
    void delete() throws Exception {
        doDelete(ADMIN_DISH_URL_TEST + DISH_ID_1);
        assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_1, MENU_ID_1));
    }
}