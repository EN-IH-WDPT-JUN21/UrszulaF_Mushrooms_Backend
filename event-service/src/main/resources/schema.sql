DROP TABLE if exists event_table;


CREATE TABLE `event_table` (
    `id` BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `event_name` VARCHAR(255),
    `event_type` VARCHAR(255),
    `when_event` VARCHAR(255),
    `duration` INT,
    `where_event` VARCHAR(255),
    `contact_person` VARCHAR(255),
    `description` VARCHAR(255)
);