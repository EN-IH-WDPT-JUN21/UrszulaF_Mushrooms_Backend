DROP TABLE if exists mushroom;


CREATE TABLE `mushroom` (
    `id` BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `photoURL` VARCHAR(255),
    `mushroom_name` VARCHAR(255),
    `other_names` VARCHAR(255),
    `edible` BIT,
    `consumable` VARCHAR(255),
    `when_fruiting` VARCHAR(255),
    `where_fruiting` VARCHAR(255),
    `hat` VARCHAR(255),
    `stem` VARCHAR(255),
    `ring` VARCHAR(255),
    `gills` VARCHAR(255),
    `tubes` VARCHAR(255),
    `pulp` VARCHAR(255),
    `smell` VARCHAR(255),
    `taste` VARCHAR(255),
    `differentiation` VARCHAR(255),
    `similar` VARCHAR(255),
    `remarks` VARCHAR(255),
    `food_value` VARCHAR(255)

);