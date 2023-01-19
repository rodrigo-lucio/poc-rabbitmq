CREATE TABLE "order" (
  id uuid NOT NULL,
  status VARCHAR(255) NOT NULL,
  expected_delivery_date DATE,
  created_at TIMESTAMP WITHOUT TIME ZONE,
  updated_at TIMESTAMP WITHOUT TIME ZONE,
  CONSTRAINT pk_order_id PRIMARY KEY (id)
);
