INSERT INTO USERS (id, name, email, password)
VALUES (1, 'User One', 'user.one@ukr.net', 'password'),
       (2, 'User Two', 'user.two@ukr.net', 'password'),
       (3, 'Admin One', 'admin.one@gmail.com', 'admin'),
       (4, 'Admin Two', 'admin.two@gmail.com', 'admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('ROLE_USER', 1),
       ('ROLE_USER', 2),
       ('ROLE_ADMIN', 3),
       ('ROLE_ADMIN', 4);

INSERT INTO RESTAURANTS (id, name, address)
VALUES (1, 'Manhattan-skybar', 'вулиця Соборна, 112, Рівне, Рівненська область, 33000'),
       (2, 'Gastro', 'проспект Миру, 10, Рівне, Рівненська область, 33013'),
       (3, 'Vinograd', 'вулиця Видумка, 2 Б, Рівне, Рівненська область, 33023'),
       (4, 'Closed', 'вулиця Грушевського, 120, Рівне, Рівненська область, 33000');

INSERT INTO MENUS (id, registered, restaurant_id)
VALUES (1, '2020-01-01', 1),
       (2, '2020-01-01', 2),
       (3, '2020-01-01', 3);

INSERT INTO MENUS (id, restaurant_id)
VALUES (4, 1),
       (5, 2),
       (6, 3);

INSERT INTO DISHES (id, name, price, menu_id)
VALUES (1, 'Шатобріан', 99, 1),
       (2, 'Червоний борщ', 38, 1),
       (3, 'Салат з тигровими креветками під кисло-солодким соусом', 146, 1),
       (4, 'Карпаччо з лосося', 99.98, 2),
       (5, 'Салат цезар', 110.50, 2),
       (6, 'Хінкалі з баранини', 97, 3),
       (7, 'Шашлик із телятини', 85, 3),
       (8, 'Курча тапака', 70, 3),
       (9, 'Шатобріан', 99, 4),
       (10, 'Червоний борщ', 38, 4),
       (11, 'Салат з тигровими креветками під кисло-солодким соусом', 146, 4),
       (12, 'Карпаччо з лосося', 99.98, 5),
       (13, 'Салат цезар', 110.50, 6),
       (14, 'Хінкалі з баранини', 97, 6),
       (15, 'Курча тапака', 70, 6);