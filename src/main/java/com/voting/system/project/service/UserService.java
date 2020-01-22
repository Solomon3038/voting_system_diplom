package com.voting.system.project.service;

import com.voting.system.project.model.User;
import com.voting.system.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.voting.system.project.util.ValidationUtil.checkNotExistWithId;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User get(int id) {
        User user = userRepository.findUserById(id);
        return checkNotExistWithId(user, id);
    }
}