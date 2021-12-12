DROP TABLE if exists animal;


CREATE TABLE `animal` (
    `id` BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `photoURL` VARCHAR(255),
    `animal_name` VARCHAR(255),
    `other_names` VARCHAR(255),
    `animal_type` VARCHAR(255),
    `animal_size` VARCHAR(255),
    `description` VARCHAR(255),
    `remarks` VARCHAR(255)
);