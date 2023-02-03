CREATE TABLE person (
    id UUID NOT NULL,
    name VARCHAR(255),
    document VARCHAR(255),
    email VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_person PRIMARY KEY (id)
);
