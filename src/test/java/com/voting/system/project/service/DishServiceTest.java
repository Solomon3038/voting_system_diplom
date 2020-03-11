package com.voting.system.project.service;

import com.voting.system.project.AbstractTest;
import org.springframework.beans.factory.annotation.Autowired;

class DishServiceTest extends AbstractTest {

    @Autowired
    private DishService dishService;

//    @Test
//    void getAll() {
//        final List<DishTo> dishTos = dishService.getAll(MENU_ID_1);
//        final List<Dish> dishes = Arrays.asList(mapper.map(dishTos, Dish[].class));
//        assertMatch(dishes, DISH_1_3, DISH_1_2, DISH_1_1);
//    }
//
//    @Test
//    void getAllNotExist() {
//        final List<DishTo> dishTos = dishService.getAll(NOT_EXIST_ID);
//        assertTrue(dishTos.isEmpty());
//    }
//
//    @Test
//    void get() {
//        assertMatch(dishService.get(DISH_ID_1, MENU_ID_1), DISH_1_1);
//    }
//
//    @Test
//    void getNotExist() {
//        assertThrows(NotExistException.class, () -> dishService.get(NOT_EXIST_ID, MENU_ID_1));
//    }
//
//    @Test
//    void getNotOwn() {
//        assertThrows(NotExistException.class, () -> dishService.get(DISH_ID_1, MENU_ID_2));
//    }
//
//    @Test
//    void save() {
//        final Dish actual = dishService.create(getNewDish(), MENU_ID_1);
//        checkSave(actual);
//    }
//
//    @Test
//    void saveNullError() {
//        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
//                () -> dishService.create(null, MENU_ID_1));
//        assertEquals("dish must not be null", exception.getMessage());
//    }
//
//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    void update() {
//        dishService.update(getUpdatedDish(DISH_1_1), DISH_ID_1, MENU_ID_1);
//        final DishTo actual = dishService.get(DISH_ID_1, MENU_ID_1);
//        assertMatch(mapper.map(actual, Dish.class), getUpdatedDish(DISH_1_1));
//    }
//
//    @Test
//    void updateNullError() {
//        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
//                () -> dishService.update(null, DISH_ID_1, MENU_ID_1));
//        assertEquals("dish must not be null", exception.getMessage());
//    }
//
//    @Test
//    void updateNotOwn() {
//        assertThrows(NotExistException.class,
//                () -> dishService.update(getUpdatedDish(DISH_1_1), DISH_ID_1, MENU_ID_2));
//    }
//
//    @Test
//    void updateNotExist() {
//        final Dish updatedDish = getUpdatedDish(DISH_1_1);
//        updatedDish.setId(NOT_EXIST_ID);
//        assertThrows(NotExistException.class, () -> {
//            dishService.update(updatedDish, NOT_EXIST_ID, MENU_ID_1);
//        });
//    }
//
//    @Test
//    void delete() {
//        dishService.delete(DISH_ID_1, MENU_ID_1);
//        assertThrows(NotExistException.class,
//                () -> dishService.get(DISH_ID_1, MENU_ID_1));
//    }
//
//    @Test
//    void deleteNotOwn() {
//        assertThrows(NotExistException.class,
//                () -> dishService.delete(DISH_ID_1, MENU_ID_2));
//    }
//
//    @Test
//    void deleteNotExist() {
//        assertThrows(NotExistException.class,
//                () -> dishService.delete(NOT_EXIST_ID, MENU_ID_1));
//    }
}