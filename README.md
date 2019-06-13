# Test-task

Перед запуском необходимо создать БД localhost:5432/TestDB
логин cp_user
пароль mypass123

создать 2 таблицы:

CREATE TABLE organization (
    id serial PRIMARY KEY NOT NULL,
    name varchar(100) NOT NULL
);

CREATE TABLE users (
    id serial PRIMARY KEY NOT NULL,
    first_name varchar(100) NOT NULL,
    last_name varchar(100) NOT NULL,
    middle_name varchar(100) NOT NULL,
    gender varchar(10) NOT NULL,
    birthday date not null,
    organization_id serial REFERENCES organization
);