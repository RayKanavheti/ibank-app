package com.equals.accountservice.repository;


import com.equals.accountservice.domain.Statement;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface StatementRepository extends ReactiveCrudRepository<Statement, Long> {


    @Query("SELECT balance FROM statements  WHERE accountNumber = :accountNumber AND  postDate <= :targetDate ORDER BY DESC LIMIT 1")
    Mono<BigDecimal> getRecentBalance(LocalDateTime targetDate, String accountNumber);

    Flux<Statement> findStatementsByAccountNumber(String accountNumber);


    @Query("SELECT * FROM statements " +
            "WHERE account_number = :accountNumber " +
            "AND (:startDate IS NULL OR post_date >= :startDate) " +
            "AND (:endDate IS NULL OR post_date <= :endDate) " +
            "ORDER BY post_date ASC " +
            "LIMIT :pageSize OFFSET :offset")
    Flux<Statement> findByAccountNumberAndDateRange(
            String accountNumber,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int pageSize,
            int offset);

    @Query("SELECT COUNT(*) FROM statements " +
            "WHERE account_number = :accountNumber " +
            "AND (:startDate IS NULL OR post_date >= :startDate) " +
            "AND (:endDate IS NULL OR post_date <= :endDate)")
    Mono<Long> countByAccountNumberAndDateRange(
            String accountNumber,
            LocalDateTime startDate,
            LocalDateTime endDate);

  }
