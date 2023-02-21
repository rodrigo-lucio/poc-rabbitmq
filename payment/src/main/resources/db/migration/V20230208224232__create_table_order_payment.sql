CREATE TABLE order_payment
(
    id                 UUID        ,
    credit_card_number VARCHAR(50) ,
    card_holder_name   VARCHAR(255),
    validity_month     INTEGER     ,
    validity_year      INTEGER     ,
    security_code      INTEGER     ,
    amount             DECIMAL     ,
    status             VARCHAR(255),
    date_confirmed     TIMESTAMP WITHOUT TIME ZONE,
    created_at         TIMESTAMP WITHOUT TIME ZONE,
    updated_at         TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_order_payment PRIMARY KEY (id)
);