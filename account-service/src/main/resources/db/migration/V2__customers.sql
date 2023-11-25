CREATE TABLE customers (
                           id SERIAL PRIMARY KEY,
                           first_name VARCHAR(255),
                           last_name VARCHAR(255),
                           phone_number VARCHAR(20),
                           address VARCHAR(255),
                           user_id BIGINT,
                           FOREIGN KEY (user_id) REFERENCES users(id)
);