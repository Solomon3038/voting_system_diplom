package com.voting.system.project.config;

import com.voting.system.project.model.User;
import com.voting.system.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//https://www.codeflow.site/ru/article/spring-security-authentication-with-a-database
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(name);
        if (user == null) {
            throw new UsernameNotFoundException(name);
        }
        return new UserPrincipal(user);
    }
}
