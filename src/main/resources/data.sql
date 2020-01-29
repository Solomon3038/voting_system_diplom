INSERT INTO USERS (id, name, email, password)
VALUES (1, 'User One', 'user.one@ukr.net', '{noop}password'),
       (2, 'User Two', 'user.two@ukr.net', '{noop}password'),
       (3, 'Admin One', 'admin.one@gmail.com', '{noop}admin'),
       (4, 'Admin Two', 'admin.two@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('USER', 2),
       ('ADMIN', 3),
       ('ADMIN', 4);

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
VALUES (1, 'Шатобріан', 9900, 1),
       (2, 'Червоний борщ', 3800, 1),
       (3, 'Салат з тигровими креветками під кисло-солодким соусом', 14600, 1),
       (4, 'Карпаччо з лосося', 9998, 2),
       (5, 'Салат цезар', 11050, 2),
       (6, 'Хінкалі з баранини', 9700, 3),
       (7, 'Шашлик із телятини', 8500, 3),
       (8, 'Курча тапака', 7000, 3),
       (9, 'Шатобріан', 9900, 4),
       (10, 'Червоний борщ', 3800, 4),
       (11, 'Салат з тигровими креветками під кисло-солодким соусом', 14600, 4),
       (12, 'Карпаччо з лосося', 9998, 5),
       (13, 'Салат цезар', 11050, 6),
       (14, 'Хінкалі з баранини', 9700, 6),
       (15, 'Курча тапака', 7000, 6);

INSERT INTO VOTES (restaurant_id, user_id)
VALUES (1, 2);