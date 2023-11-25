package com.equals.accountservice.service;

import com.equals.accountservice.domain.Statement;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface StatementService {
    Mono<Statement> saveStatement(Statement statement);
    Flux<Statement> getStatementByAccountNumber(String accountNumber);

    Flux<Statement> getStatementByAccountNumberAndDateRange(String accountNumber,
                                                             LocalDateTime startDate,
                                                             LocalDateTime endDate,
                                                            PageRequest pageRequest);

    Mono<Long> countByAccountNumberAndDateRange( String accountNumber,
                                                 LocalDateTime startDate,
                                                 LocalDateTime endDate);
}
