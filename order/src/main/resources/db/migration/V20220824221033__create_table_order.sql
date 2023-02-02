CREATE TABLE "order" (
  id UUID NOT NULL,
  status VARCHAR(255) NOT NULL,
  expected_delivery_date DATE,
  person_id UUID NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE,
  updated_at TIMESTAMP WITHOUT TIME ZONE,
  CONSTRAINT pk_order_id PRIMARY KEY (id),
  CONSTRAINT fk_person_id_person FOREIGN KEY (person_id) REFERENCES person(id)
);
