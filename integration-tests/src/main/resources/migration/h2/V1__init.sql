CREATE TABLE verified
(
    ID         BIGINT PRIMARY KEY AUTO_INCREMENT,
    PRODUCT_ID UUID         not null,
    STATUS     varchar(255) not null
);