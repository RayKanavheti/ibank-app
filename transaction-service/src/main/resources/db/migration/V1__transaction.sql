CREATE TABLE transactions
(
    id               SERIAL PRIMARY KEY,
    transaction_date TIMESTAMP,
    transaction_type VARCHAR(50), -- or ENUM type if supported by your PostgreSQL version
    reference        VARCHAR(255),
    amount           NUMERIC,
    from_account     VARCHAR(255),
    to_account       VARCHAR(255)
);