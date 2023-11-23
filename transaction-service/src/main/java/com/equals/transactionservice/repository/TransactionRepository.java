package com.equals.transactionservice.repository;

import com.equals.transactionservice.domain.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Long> {

}
