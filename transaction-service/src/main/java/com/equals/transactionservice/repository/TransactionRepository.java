package com.equals.transactionservice.repository;

import com.equals.transactionservice.domain.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Long> {

    Flux<Transaction> findTransactionsByFromAccountOrToAccountOrderByTransactionDateDesc(String fromAccount, String toAccount);
    Flux<Transaction> findTop5ByFromAccountOrToAccountOrderByTransactionDateDesc(String fromAccount, String toAccount);

}
