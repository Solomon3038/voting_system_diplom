INSERT INTO USERS (name, email, password)
VALUES ('User One', 'user.one@ukr.net', 'password'),
       ('User Two', 'user.two@ukr.net', 'password'),
       ('Admin One', 'admin.one@gmail.com', 'admin'),
       ('Admin Two', 'admin.two@gmail.com', 'admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('ROLE_USER', 1),
       ('ROLE_USER', 2),
       ('ROLE_ADMIN', 3),
       ('ROLE_ADMIN', 4);