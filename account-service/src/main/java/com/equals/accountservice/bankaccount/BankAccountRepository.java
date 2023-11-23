package com.equals.accountservice.bankaccount;

import com.equals.accountservice.customer.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BankAccountRepository extends ReactiveCrudRepository<BankAccount, Long> {
}
