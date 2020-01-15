drop table if exists dishes;
drop table if exists menus;
drop table if exists restaurants;
drop table if exists user_roles;
drop table if exists users;

create table restaurants
(
    ID   INTEGER auto_increment primary key,
    NAME VARCHAR(100) not null
);

create table menus
(
    ID            INTEGER auto_increment primary key,
    REGISTERED    TIMESTAMP default NOW() not null,
    RESTAURANT_ID INTEGER                 not null,
    constraint MENUS_UNIQUE_REGISTERED_IDX unique (REGISTERED, ID),
    constraint FK49THMNYTVO47TTV4GGTWO9RRJ foreign key (RESTAURANT_ID) references RESTAURANTS (ID)
);

create table dishes
(
    ID      INTEGER auto_increment primary key,
    NAME    VARCHAR(100) not null,
    PRICE   BIGINT       not null,
    MENU_ID INTEGER      not null,
    constraint USERS_UNIQUE_EMAIL_IDX unique (NAME, MENU_ID),
    constraint FKPCWEPST3FW5EXFGGPSM1IS56D  foreign key (MENU_ID) references MENUS (ID)
);

create table users
(
    ID         INTEGER auto_increment primary key,
    NAME       VARCHAR(100)            not null,
    EMAIL      VARCHAR(100)            not null constraint UK_6DOTKOTT2KJSP8VW4D0M25FB7 unique,
    PASSWORD   VARCHAR(100)            not null,
    REGISTERED TIMESTAMP default NOW() not null
);

create table user_roles
(
    USER_ID INTEGER not null,
    ROLE    VARCHAR(255),
    constraint FKHFH9DX7W3UBF1CO1VDEV94G3F foreign key (USER_ID) references USERS (ID)
);