package com.voting.system.project.web;

import com.voting.system.project.model.Dish;
import com.voting.system.project.service.DishService;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.voting.system.project.TestDataHelper.ADMIN_1_EMAIL;
import static com.voting.system.project.TestDataHelper.DISH_1;
import static com.voting.system.project.TestDataHelper.DISH_2;
import static com.voting.system.project.TestDataHelper.DISH_4;
import static com.voting.system.project.TestDataHelper.DISH_ID_1;
import static com.voting.system.project.TestDataHelper.DISH_ID_4;
import static com.voting.system.project.TestDataHelper.DISH_ID_NEXT;
import static com.voting.system.project.TestDataHelper.NOT_EXIST_ID;
import static com.voting.system.project.TestDataHelper.RESTAURANT_1;
import static com.voting.system.project.TestDataHelper.RESTAURANT_1_DISHES;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_1;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_2;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_NEXT;
import static com.voting.system.project.TestDataHelper.getNewDish;
import static com.voting.system.project.TestDataHelper.getUpdatedDish;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(ADMIN_1_EMAIL)
class AdminDishControllerTest extends AbstractControllerTest {

    public static final String ADMIN_DISH_URL_TEST = "/admin/restaurants/" + RESTAURANT_ID_1 + "/dishes/";
    public static final String ADMIN_DISH_NOT_EXIST_URL_TEST = "/admin/restaurants/" + RESTAURANT_ID_NEXT + "/dishes/";

    @Autowired
    private DishService dishService;

    @Test
    void getAll() throws Exception {
        doGet(ADMIN_DISH_URL_TEST, objectMapper.writeValueAsString(RESTAURANT_1_DISHES));
    }

    @Test
    void get() throws Exception {
        doGet(ADMIN_DISH_URL_TEST + DISH_ID_1, objectMapper.writeValueAsString(DISH_1));
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
        final Dish newDish = getNewDish();
        final String dish = objectMapper.writeValueAsString(newDish);
        final String result = doPost(dish, ADMIN_DISH_URL_TEST);
        final Dish created = objectMapper.readValue(result, Dish.class);
        newDish.setId(created.getId());
        assertMatch(dishService.get(created.getId(), RESTAURANT_ID_1), newDish);
    }

    @Test
    void createNotNew() throws Exception {
        final String dish = objectMapper.writeValueAsString(new Dish(DISH_ID_NEXT, "New Name", RESTAURANT_1));
        doPostErr(dish, ADMIN_DISH_URL_TEST, status().isUnprocessableEntity());
        assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_NEXT, RESTAURANT_ID_1));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createNotExist() throws Exception {
        final String dish = objectMapper.writeValueAsString(new Dish(null, "New Name", RESTAURANT_1));
        doPostErr(dish, ADMIN_DISH_NOT_EXIST_URL_TEST, status().isConflict());
        assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_NEXT, RESTAURANT_ID_NEXT));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicateData() throws Exception {
        final String dish = objectMapper.writeValueAsString(new Dish(null, DISH_1.getName(), RESTAURANT_1));
        doPostErr(dish, ADMIN_DISH_URL_TEST, status().isConflict());
        assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_NEXT, RESTAURANT_ID_1));
    }

    @Test
    void createInvalidData() throws Exception {
        final String dish = objectMapper.writeValueAsString(new Dish(null, "D", RESTAURANT_1));
        doPostErr(dish, ADMIN_DISH_URL_TEST, status().isUnprocessableEntity());
        assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_NEXT, RESTAURANT_ID_1));
    }

    @Test
    void update() throws Exception {
        final Dish updatedDish = getUpdatedDish(DISH_1);
        final String dish = objectMapper.writeValueAsString(updatedDish);
        doPut(dish, ADMIN_DISH_URL_TEST + DISH_ID_1);
        assertMatch(dishService.get(DISH_ID_1, RESTAURANT_ID_1), updatedDish);
    }

    @Test
    void updateNotExist() throws Exception {
        final String dish = objectMapper.writeValueAsString(getUpdatedDish(DISH_1));
        doPutErr(dish, ADMIN_DISH_NOT_EXIST_URL_TEST + DISH_ID_1, status().isUnprocessableEntity());
        assertMatch(dishService.get(DISH_ID_1, RESTAURANT_ID_1), DISH_1);
    }

    @Test
    void updateNotOwn() throws Exception {
        final String dish = objectMapper.writeValueAsString(getUpdatedDish(DISH_4));
        doPutErr(dish, ADMIN_DISH_URL_TEST + DISH_ID_4, status().isUnprocessableEntity());
        assertMatch(dishService.get(DISH_ID_4, RESTAURANT_ID_2), DISH_4);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicateData() throws Exception {
        doPutErr(DISH_2.getName(), status().isConflict());
    }

    @Test
    void updateInvalidData() throws Exception {
        doPutErr("D", status().isUnprocessableEntity());
    }

    private void doPutErr(String name, ResultMatcher unprocessableEntity) throws Exception {
        final String dish = objectMapper.writeValueAsString(new Dish(DISH_ID_1, name, RESTAURANT_1));
        doPutErr(dish, ADMIN_DISH_URL_TEST + DISH_ID_1, unprocessableEntity);
        assertMatch(dishService.get(DISH_ID_1, RESTAURANT_ID_1), DISH_1);
    }
}