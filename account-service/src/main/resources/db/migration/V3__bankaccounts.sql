CREATE TABLE bank_accounts
(
    id              SERIAL PRIMARY KEY,
    account_number  VARCHAR(255) NOT NULL,
    account_balance NUMERIC,
    account_type    VARCHAR(50), -- or ENUM type if supported by your PostgreSQL version
    customer_id     BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customers (id)
);