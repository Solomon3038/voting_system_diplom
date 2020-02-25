package com.voting.system.project.util;

import com.voting.system.project.auth.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.requireNonNull;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static UserPrincipal safeGet() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        final Object principal = auth.getPrincipal();
        return (principal instanceof UserPrincipal) ? (UserPrincipal) principal : null;
    }

    public static UserPrincipal get() {
        final UserPrincipal user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public static int authUserId() {
        return get().getId();
    }
}