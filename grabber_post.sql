create database grabber;
create table post (
    id int primary key,
    name text,
    text text,
    link text unique,
    created timestamp
);