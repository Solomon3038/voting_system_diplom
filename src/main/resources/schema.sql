drop table if exists dishes;
drop table if exists menus;
drop table if exists restaurants;
drop table if exists user_roles;
drop table if exists users;

create table restaurants
(
    ID   INTEGER auto_increment primary key,
    NAME VARCHAR(100) not null,
    ADDRESS VARCHAR(100) not null,
    constraint NAME_ADDRESS_UNIQUE_IDX unique (NAME, ADDRESS)
);

create table menus
(
    ID            INTEGER auto_increment primary key,
    REGISTERED    TIMESTAMP default NOW() not null,
    RESTAURANT_ID INTEGER                 not null,
    constraint MENUS_UNIQUE_REGISTERED_IDX unique (REGISTERED, ID),
    constraint FK_RESTAURANT_ID foreign key (RESTAURANT_ID) references RESTAURANTS (ID)
);

create table dishes
(
    ID      INTEGER auto_increment primary key,
    NAME    VARCHAR(100) not null,
    PRICE   BIGINT       not null,
    MENU_ID INTEGER      not null,
    constraint USERS_UNIQUE_EMAIL_IDX unique (NAME, MENU_ID),
    constraint FK_MENU_ID foreign key (MENU_ID) references MENUS (ID)
);

create table users
(
    ID         INTEGER auto_increment primary key,
    NAME       VARCHAR(100)            not null,
    EMAIL      VARCHAR(100)            not null
        constraint UK_EMAIL unique,
    PASSWORD   VARCHAR(100)            not null,
    REGISTERED TIMESTAMP default NOW() not null
);

create table user_roles
(
    USER_ID INTEGER not null,
    ROLE    VARCHAR(255),
    constraint FK_USER_ID foreign key (USER_ID) references USERS (ID)
);