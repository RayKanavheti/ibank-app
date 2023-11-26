package com.equals.accountservice.repository;

import com.equals.accountservice.domain.BankAccount;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

public interface BankAccountRepository extends ReactiveCrudRepository<BankAccount, Long> {


    @Modifying
    @Query(value = "UPDATE bank_accounts SET account_balance = account_balance + :amount WHERE account_number = :accountNumber" )
    @CacheEvict(cacheNames = "statementByAccountNumber", key = "#accountNumber")
    Mono<Integer> addBankAccountBalance(BigDecimal amount, String accountNumber);

    @Modifying
    @Query(value = "UPDATE bank_accounts SET account_balance = account_balance - :amount WHERE account_number = :accountNumber" )
   @CacheEvict(cacheNames = "statementByAccountNumber", key = "#accountNumber")
    Mono<Integer> subtractBankAccountBalance(BigDecimal amount, String accountNumber);


    Mono<BankAccount> findBankAccountByAccountNumber(String accountNumber);

    @Query("SELECT * FROM bank_accounts WHERE account_number IN (:accountNumbers)")
    Flux<BankAccount> findByAccountNumbers(List<String> accountNumbers);
}
