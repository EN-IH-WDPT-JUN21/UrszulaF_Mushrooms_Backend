DROP TABLE if exists user;
DROP TABLE if exists image_table;

CREATE TABLE `user` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `photoURL` VARCHAR(255),
    `username` VARCHAR(255),
    `bio` VARCHAR(255)
);

CREATE TABLE `image_table` (
    `id` BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `name` VARCHAR(255),
    `pic_byte` LONGBLOB,
    `type` VARCHAR(255)
);

/*
create database mushroomapp;

use mushroomapp;

drop USER 'ironhacker'@'localhost';

CREATE USER 'ironhacker'@'localhost' IDENTIFIED BY '1r0nH@ck3r';

GRANT ALL PRIVILEGES ON *.* TO 'ironhacker'@'localhost';
*/