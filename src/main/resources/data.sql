INSERT INTO USERS (id, name, email, password)
VALUES (1, 'User One', 'user.one@ukr.net', '{noop}password'),
       (2, 'User Two', 'user.two@ukr.net', '{noop}password'),
       (3, 'Admin One', 'admin.one@gmail.com', '{noop}admin'),
       (4, 'Admin Two', 'admin.two@gmail.com', '{noop}admin');

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

INSERT INTO DISHES (id, name)
VALUES (1, 'Шатобріан'),
       (2, 'Червоний борщ'),
       (3, 'Салат з тигровими креветками під кисло-солодким соусом'),
       (4, 'Карпаччо з лосося'),
       (5, 'Салат цезар'),
       (6, 'Хінкалі з баранини'),
       (7, 'Шашлик із телятини'),
       (8, 'Курча тапака');

INSERT INTO MENU_ITEMS (id, date, price, dish_id, restaurant_id)
VALUES (1, '2019-06-01', 9000, 1, 1),
       (2, '2019-06-01', 3800, 2, 1),
       (3, '2019-06-01', 14600, 3, 1),
       (4, '2019-06-01', 9998, 4, 2),
       (5, '2019-06-01', 11050, 5, 2),
       (6, '2019-06-01', 9700, 6, 2),
       (7, '2019-06-01', 8500, 7, 3),
       (8, '2019-06-01', 7000, 8, 3),
       (9, '2019-06-01', 9998, 4, 3);

INSERT INTO MENU_ITEMS (id, price, dish_id, restaurant_id)
VALUES (10, 9000, 1, 1),
       (11, 3800, 2, 1),
       (12, 9998, 4, 2),
       (13, 9700, 6, 2),
       (14, 9998, 4, 3);

INSERT INTO VOTES (restaurant_id, user_id)
VALUES (1, 2);

INSERT INTO VOTES (date, restaurant_id, user_id)
VALUES ('2020-01-01', 3, 1),
       ('2020-01-01', 2, 2);