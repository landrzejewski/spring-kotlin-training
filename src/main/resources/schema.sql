CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     username VARCHAR NOT NULL,
                                     email VARCHAR NOT NULL,
                                     CONSTRAINT unique_user UNIQUE (username, email)
    );