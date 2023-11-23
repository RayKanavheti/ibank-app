package com.equals.transactionservice.repository;


import com.equals.transactionservice.domain.Statement;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface StatementRepository extends ReactiveCrudRepository<Statement, Long> {


    @Query("SELECT balance FROM statements  WHERE accountNumber = :accountNumber AND  postDate <= :targetDate ORDER BY DESC LIMIT 1")
    Mono<BigDecimal> getRecentBalance(LocalDateTime targetDate, String accountNumber);

}
