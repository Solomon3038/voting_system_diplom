package com.voting.system.project.repository;

import com.voting.system.project.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voting.system.project.TestDataHelper.USER_1;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByEmail() {
        final User actual = userRepository.findUserByEmail(USER_1.getEmail());
        assertMatch(actual, USER_1);
    }

    @Test
    void findUserByEmailNotExist() {
        final User userNotExist = userRepository.findUserByEmail("emailNotExist");
        assertNull(userNotExist);
    }
}