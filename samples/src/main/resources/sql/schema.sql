CREATE TABLE `user`
(
    `id`       int NOT NULL AUTO_INCREMENT,
    `name`     varchar(64)  DEFAULT NULL,
    `age`      int          DEFAULT NULL,
    `address`  varchar(64)  DEFAULT NULL,
    `phone`    varchar(20)  DEFAULT NULL,
    `password` varchar(64)  DEFAULT NULL,
    `b64`      varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;