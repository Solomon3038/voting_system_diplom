package com.voting.system.project.util;

import com.voting.system.project.model.Dish;

import static com.voting.system.project.TestData.getNewDish;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;

public class DishTestUtil {

    private DishTestUtil() {
    }

    public static void checkSave(Dish actual) {
        Dish expected = getNewDish();
        expected.setId(actual.getId());
        assertMatch(actual, expected);
    }
}