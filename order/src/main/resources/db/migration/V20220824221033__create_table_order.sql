CREATE TABLE "order" (
  id uuid NOT NULL,
  status varchar(255) NOT NULL,
  created_at timestamp,
  updated_at timestamp,
  CONSTRAINT pk_order_id PRIMARY KEY (id)
);
