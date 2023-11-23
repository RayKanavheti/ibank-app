package com.equals.accountservice.bankaccount;

import com.equals.accountservice.customer.Customer;
import reactor.core.publisher.Mono;

public interface BankAccountService {

    Mono<BankAccount> createBankAccount(BankAccount bankAccount);
}
