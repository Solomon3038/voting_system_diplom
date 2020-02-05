package com.voting.system.project.config;

import com.voting.system.project.model.User;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UserPrincipal extends org.springframework.security.core.userdetails.User {
    @NotNull
    private User user;

    public UserPrincipal(User user) {
        super(user.getName(), user.getPassword(), true, true, true, true, user.getRoles());
        this.user = user;
    }

    public Integer getId() {
        return user.id();
    }
}
