CREATE TABLE statements
(
    id             SERIAL PRIMARY KEY,
    debit_amount   NUMERIC,
    credit_amount  NUMERIC,
    balance        NUMERIC,
    narrative      VARCHAR(255),
    transaction_id BIGINT,
    reference      VARCHAR(255),
    post_date      TIMESTAMP,
    account_number VARCHAR(255)
)