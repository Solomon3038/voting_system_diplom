package com.voting.system.project.repository;

import com.voting.system.project.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;

class UserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByEmail() {
        User actual = userRepository.findUserByEmail(USER_1.getEmail());
        assertMatch(actual, USER_1);
    }

    @Test
    void findUserByEmailNotExist() {
        User userNotExist = userRepository.findUserByEmail("emailNotExist");
        Assertions.assertNull(userNotExist);
    }
}