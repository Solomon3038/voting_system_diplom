package com.voting.system.project;

import com.voting.system.project.model.Role;
import com.voting.system.project.model.User;
import org.assertj.core.api.Assertions;

public class TestData {
    public static final int USER_ID_1 = 1;
    public static final int USER_ID_2 = 2;
    public static final int USER_ID_3 = 3;
    public static final int USER_ID_4 = 4;
    public static final int USER_ID_NOT_EXIST= -1;

    public static final User USER_1 = new User(USER_ID_1, "User One", "user.one@ukr.net", "password", Role.ROLE_USER);
    public static final User USER_2 = new User(USER_ID_2, "User Two", "user.two@ukr.net", "password", Role.ROLE_USER);
    public static final User ADMIN_1 = new User(USER_ID_3, "Admin One", "admin.one@gmail.com", "admin", Role.ROLE_ADMIN);
    public static final User ADMIN_2 = new User(USER_ID_4, "Admin Two", "admin.two@gmail.com", "admin", Role.ROLE_ADMIN);

    public static void assertMatch(User actual, User expected) {
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected,"registered");
    }
}
