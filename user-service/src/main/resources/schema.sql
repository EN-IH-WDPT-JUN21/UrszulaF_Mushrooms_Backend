DROP TABLE if exists user;


CREATE TABLE `user` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `photoURL` VARCHAR(255),
    `username` VARCHAR(255),
    `bio` VARCHAR(255)
);

/*
create database mushroomapp;

use mushroomapp;

drop USER 'ironhacker'@'localhost';

CREATE USER 'ironhacker'@'localhost' IDENTIFIED BY '1r0nH@ck3r';

GRANT ALL PRIVILEGES ON *.* TO 'ironhacker'@'localhost';
*/