DROP TABLE if exists user_auth;


CREATE TABLE `user_auth` (
    `id` BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `username` VARCHAR(255),
    `email` VARCHAR(255),
    `password` VARCHAR(255),
    `role` VARCHAR(255)
);

/*
create database mushroomapp;

use mushroomapp;

drop USER 'ironhacker'@'localhost';

CREATE USER 'ironhacker'@'localhost' IDENTIFIED BY '1r0nH@ck3r';

GRANT ALL PRIVILEGES ON *.* TO 'ironhacker'@'localhost';
*/