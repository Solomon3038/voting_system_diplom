package com.voting.system.project.repository;

import com.voting.system.project.ProjectApplicationTests;
import com.voting.system.project.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.TestMatcherUtil.*;

class UserRepositoryTest extends ProjectApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findById() {
        User firstAdmin = userRepository.findUserById(ADMIN_ID_1);
        assertMatch(firstAdmin, ADMIN_1);
    }

    @Test
    void findByIdNotExist() {
        User userNotExist = userRepository.findUserById(NOT_EXIST_ID);
        Assertions.assertNull(userNotExist);
    }
}