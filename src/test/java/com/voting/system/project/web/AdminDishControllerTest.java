package com.voting.system.project.web;

import com.voting.system.project.service.DishService;
import com.voting.system.project.to.DishTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.voting.system.project.TestDataHelper.ADMIN_1_EMAIL;
import static com.voting.system.project.TestDataHelper.DISH_1_1;
import static com.voting.system.project.TestDataHelper.DISH_1_2;
import static com.voting.system.project.TestDataHelper.DISH_1_3;
import static com.voting.system.project.TestDataHelper.DISH_2_1;
import static com.voting.system.project.TestDataHelper.DISH_ID_1;
import static com.voting.system.project.TestDataHelper.DISH_ID_4;
import static com.voting.system.project.TestDataHelper.DISH_ID_NEXT;
import static com.voting.system.project.TestDataHelper.MENU_ID_1;
import static com.voting.system.project.TestDataHelper.MENU_ID_2;
import static com.voting.system.project.TestDataHelper.MENU_ID_NEXT;
import static com.voting.system.project.TestDataHelper.NOT_EXIST_ID;
import static com.voting.system.project.TestDataToHelper.getNewDishTo;
import static com.voting.system.project.TestDataToHelper.getUpdatedDishTo;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(ADMIN_1_EMAIL)
class AdminDishControllerTest extends AbstractControllerTest {

    public static final String ADMIN_DISH_URL_TEST = "/admin/menus/" + MENU_ID_1 + "/dishes/";
    public static final String ADMIN_DISH_NOT_EXIST_URL_TEST = "/admin/menus/" + MENU_ID_NEXT + "/dishes/";

    @Autowired
    private DishService dishService;

    @Test
    void getAll() throws Exception {
        final String dishes = objectMapper.writeValueAsString(mapper.map(Arrays.asList(DISH_1_3, DISH_1_2, DISH_1_1), DishTo[].class));
        doGet(ADMIN_DISH_URL_TEST, dishes);
    }

    @Test
    void get() throws Exception {
        final String dish = objectMapper.writeValueAsString(mapper.map(DISH_1_1, DishTo.class));
        doGet(ADMIN_DISH_URL_TEST + DISH_ID_1, dish);
    }

    @Test
    void getNotExist() throws Exception {
        doGetNotExist(ADMIN_DISH_URL_TEST + NOT_EXIST_ID);
    }

    @Test
    void getNotOwn() throws Exception {
        doGetNotExist(ADMIN_DISH_URL_TEST + DISH_ID_4);
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
    void createNotNew() throws Exception {
        final String dish = objectMapper.writeValueAsString(new DishTo(DISH_ID_NEXT, "New Name", 15_00));
        doPostErr(dish, ADMIN_DISH_URL_TEST, status().isUnprocessableEntity());
        assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_NEXT, MENU_ID_1));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createNotExist() throws Exception {
        final String dish = objectMapper.writeValueAsString(new DishTo(null, "New Name", 15_00));
        doPostErr(dish, ADMIN_DISH_NOT_EXIST_URL_TEST, status().isConflict());
        assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_NEXT, MENU_ID_NEXT));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicateData() throws Exception {
        final String dish = objectMapper.writeValueAsString(new DishTo(null, DISH_1_1.getName(), 123_00));
        doPostErr(dish, ADMIN_DISH_URL_TEST, status().isConflict());
        assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_NEXT, MENU_ID_1));
    }

    @Test
    void createInvalidData() throws Exception {
        final String dish = objectMapper.writeValueAsString(new DishTo(null, "D", 3));
        doPostErr(dish, ADMIN_DISH_URL_TEST, status().isUnprocessableEntity());
        assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_NEXT, MENU_ID_1));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void update() throws Exception {
        final DishTo updatedDish = getUpdatedDishTo(DISH_1_1);
        final String dish = objectMapper.writeValueAsString(getUpdatedDishTo(DISH_1_1));
        doPut(dish, ADMIN_DISH_URL_TEST + DISH_ID_1);
        assertMatch(dishService.get(DISH_ID_1, MENU_ID_1), updatedDish);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateNotExist() throws Exception {
        final String dish = objectMapper.writeValueAsString(getUpdatedDishTo(DISH_1_1));
        doPutErr(dish, ADMIN_DISH_NOT_EXIST_URL_TEST + DISH_ID_1, status().isUnprocessableEntity());
        assertMatch(dishService.get(DISH_ID_1, MENU_ID_1), DISH_1_1);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateNotOwn() throws Exception {
        final String dish = objectMapper.writeValueAsString(getUpdatedDishTo(DISH_2_1));
        doPutErr(dish, ADMIN_DISH_URL_TEST + DISH_ID_4, status().isUnprocessableEntity());
        assertMatch(dishService.get(DISH_ID_4, MENU_ID_2), DISH_2_1);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicateData() throws Exception {
        doPutErr(DISH_1_2.getName(), 123_00, status().isConflict());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateInvalidData() throws Exception {
        doPutErr("D", 3, status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        doDelete(ADMIN_DISH_URL_TEST + DISH_ID_1);
        assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_1, MENU_ID_1));
    }

    @Test
    void deleteNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ADMIN_DISH_URL_TEST + DISH_ID_NEXT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteNotOwn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ADMIN_DISH_URL_TEST + DISH_ID_4))
                .andExpect(status().isUnprocessableEntity());
        assertMatch(dishService.get(DISH_ID_4, MENU_ID_2), DISH_2_1);
    }

    private void doPutErr(String name, int price, ResultMatcher unprocessableEntity) throws Exception {
        final String dish = objectMapper.writeValueAsString(new DishTo(DISH_ID_1, name, price));
        doPutErr(dish, ADMIN_DISH_URL_TEST + DISH_ID_1, unprocessableEntity);
        assertMatch(dishService.get(DISH_ID_1, MENU_ID_1), DISH_1_1);
    }
}