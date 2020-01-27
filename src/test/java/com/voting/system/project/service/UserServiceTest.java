package com.voting.system.project.service;

import com.voting.system.project.model.User;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;

class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void get() {
        User actual = userService.get(USER_ID_1);
        assertMatch(actual, USER_1);
    }

    @Test
    void getNotExist() {
        Assertions.assertThrows(NotExistException.class, () -> userService.get(NOT_EXIST_ID));
    }
}