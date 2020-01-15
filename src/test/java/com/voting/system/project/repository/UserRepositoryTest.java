package com.voting.system.project.repository;

import com.voting.system.project.ProjectApplicationTests;
import com.voting.system.project.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voting.system.project.TestData.*;

class UserRepositoryTest extends ProjectApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void getUser() {
        User firstAdmin = userRepository.findUserById(USER_ID_3);
        assertMatch(firstAdmin, ADMIN_1);
    }

    @Test
    void getNotExistUser() {
        User userNotExist = userRepository.findUserById(USER_ID_NOT_EXIST);
        Assertions.assertNull(userNotExist);
    }
}