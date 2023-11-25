package com.equals.accountservice.service;

import com.equals.accountservice.domain.BankAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

public interface BankAccountService {

    Mono<BankAccount> createBankAccount(BankAccount bankAccount);

    Mono<Integer> addAccountBalance(BigDecimal amount, String accountNumber);
    Mono<Integer> subtractAccountBalance(BigDecimal amount, String accountNumber);
    Mono<BankAccount> findBankAccountByAccountNumber(String accountNumber);

    Flux<BankAccount> findByBankAccountNumbers(List<String> accountNumbers);

}
