CREATE TABLE person (
    id UUID NOT NULL,
    name VARCHAR(255),
    document VARCHAR(255),
    email VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    active BOOLEAN DEFAULT TRUE,
    CONSTRAINT pk_person PRIMARY KEY (id)
);
