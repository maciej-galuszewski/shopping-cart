CREATE SEQUENCE product_type_seq;

CREATE TABLE product_type(
    id IDENTITY,
    key VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL UNIQUE,
    price DECIMAL(20, 2) NOT NULL,
    amount_strategy VARCHAR(50) NOT NULL
);
