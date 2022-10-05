CREATE TABLE order_item (
  id uuid NOT NULL,
  order_id uuid NOT NULL,
  description varchar(255) NOT NULL,
  quantity numeric(19) NOT NULL,
  created_at timestamp,
  updated_at timestamp,
  CONSTRAINT pk_order_item_id PRIMARY KEY (id),
  CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES "order"(id)
);
