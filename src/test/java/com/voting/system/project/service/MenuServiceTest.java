package com.voting.system.project.service;

import com.voting.system.project.AbstractTest;

class MenuServiceTest extends AbstractTest {

//    @Autowired
//    private MenuService menuService;

//    @Test
//    void get() {
//        final Menu actual = mapper.map(menuService.get(MENU_ID_1, RESTAURANT_ID_1), Menu.class);
//        assertMatch(actual, MENU_1);
//    }
//
//    @Test
//    void getNotExist() {
//        assertThrows(NotExistException.class, () -> menuService.get(NOT_EXIST_ID, RESTAURANT_ID_1));
//    }
//
//    @Test
//    void getNotOwn() {
//        assertThrows(NotExistException.class, () -> menuService.get(MENU_ID_2, RESTAURANT_ID_1));
//    }
//
//    @Test
//    void getAll() {
//        final List<MenuWithDishesTo> tos = menuService.getAll(RESTAURANT_ID_1);
//        final List<Menu> actual = Arrays.asList(mapper.map(tos, Menu[].class));
//        checkAllWithDishes(actual);
//    }
//
//    @Test
//    void getAllNotExist() {
//        final List<MenuWithDishesTo> tos = menuService.getAll(NOT_EXIST_ID);
//        assertTrue(tos.isEmpty());
//    }
//
//    @Test
//    void create() {
//        final Menu saved = menuService.create(getNewMenu(), RESTAURANT_ID_4);
//        checkSave(saved);
//    }
//
//    @Test
//    void createWithDishes() {
//        final Menu saved = menuService.create(getNewMenuWithDishes(), RESTAURANT_ID_4);
//        checkSaveWithDishes(saved);
//    }
//
//    @Test
//    void createNullError() {
//        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
//                () -> menuService.create(null, RESTAURANT_ID_4));
//        assertEquals("menu must not be null", exception.getMessage());
//    }
//
//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    void update() {
//        menuService.update(getUpdatedMenuTo(MENU_1), MENU_ID_1, RESTAURANT_ID_1);
//        assertMatch(menuService.get(MENU_ID_1, RESTAURANT_ID_1), getUpdatedMenuTo(MENU_1));
//    }
//
//    @Test
//    void updateNullError() {
//        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
//                () -> menuService.update(null, MENU_ID_1, RESTAURANT_ID_1));
//        assertEquals("menu must not be null", exception.getMessage());
//    }
//
//    @Test
//    void updateNotExistError() {
//        assertThrows(NotExistException.class,
//                () -> menuService.update(getUpdatedMenuTo(MENU_1), MENU_ID_1, NOT_EXIST_ID));
//    }
//
//    @Test
//    void updateNotOwnError() {
//        assertThrows(NotExistException.class,
//                () -> menuService.update(getUpdatedMenuTo(MENU_1), MENU_ID_1, RESTAURANT_ID_2));
//    }
}