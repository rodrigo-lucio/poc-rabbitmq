CREATE TABLE order_payment
(
    id                 UUID         NOT NULL,
    order_id           UUID         NOT NULL,
    credit_card_number VARCHAR(50) NOT NULL,
    card_holder_name   VARCHAR(255) NOT NULL,
    validity_month     INTEGER      NOT NULL,
    validity_year      INTEGER      NOT NULL,
    security_code      INTEGER      NOT NULL,
    amount             DECIMAL      NOT NULL,
    status             VARCHAR(255) NOT NULL,
    date_confirmed     TIMESTAMP WITHOUT TIME ZONE,
    created_at         TIMESTAMP WITHOUT TIME ZONE,
    updated_at         TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_order_payment PRIMARY KEY (id)
);

ALTER TABLE order_payment
    ADD CONSTRAINT fk_order_payment_on_order FOREIGN KEY (order_id) REFERENCES "order" (id);