DROP TABLE IF EXISTS organization;

CREATE TABLE organization (
    id serial PRIMARY KEY NOT NULL,
    name varchar(100) NOT NULL
);

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id serial PRIMARY KEY NOT NULL,
    first_name varchar(100) NOT NULL,
    last_name varchar(100) NOT NULL,
    middle_name varchar(100) NOT NULL,
    gender varchar(10) NOT NULL,
    birthday date not null,
    organization_id serial REFERENCES organization
);