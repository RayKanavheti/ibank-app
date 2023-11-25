ALTER TABLE bank_accounts DROP CONSTRAINT IF EXISTS fk_bank_accounts_customers;

-- Add the unique constraint to the customer_id column
ALTER TABLE bank_accounts
    ADD CONSTRAINT unique_customer_id UNIQUE (customer_id),
    ADD CONSTRAINT unique_account_number UNIQUE (account_number);
-- Add the foreign key constraint back
ALTER TABLE bank_accounts
    ADD CONSTRAINT fk_bank_accounts_customers FOREIGN KEY (customer_id) REFERENCES customers(id);

ALTER TABLE customers DROP CONSTRAINT IF EXISTS fk_customers_users;

ALTER TABLE customers
    ADD CONSTRAINT unique_user_id UNIQUE (user_id);

ALTER TABLE customers
    ADD CONSTRAINT fk_customers_users FOREIGN KEY (user_id) REFERENCES users(id);