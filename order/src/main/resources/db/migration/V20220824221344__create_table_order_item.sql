CREATE TABLE order_item (
    id                     UUID    NOT NULL,
    order_id               UUID,
    description            VARCHAR(255),
    quantity               INTEGER NOT NULL,
    unitary_value          DECIMAL NOT NULL,
    amount                 DECIMAL NOT NULL,
    expected_delivery_date date,
    created_at             TIMESTAMP WITHOUT TIME ZONE,
    updated_at             TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_order_item_id PRIMARY KEY (id),
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES "order"(id)
);
