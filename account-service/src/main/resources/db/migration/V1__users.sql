CREATE TABLE users (
                        id bigserial PRIMARY KEY,
                        username VARCHAR(15) NOT NULL,
                        password text,
                        email text
);