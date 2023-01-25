CREATE TABLE person (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    document VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_person PRIMARY KEY (id)
);
