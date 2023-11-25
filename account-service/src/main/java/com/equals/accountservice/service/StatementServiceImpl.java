package com.equals.accountservice.service;

import com.equals.accountservice.domain.Statement;
import com.equals.accountservice.repository.StatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {
    private final StatementRepository statementRepository;
    @Override
    public Mono<Statement> saveStatement(Statement statement) {
        return statementRepository.save(statement);
    }

    @Override
    public Flux<Statement> getStatementByAccountNumber(String accountNumber) {
        return statementRepository.findStatementsByAccountNumber(accountNumber);
    }


    @Override
    public Flux<Statement> getStatementByAccountNumberAndDateRange(String accountNumber, LocalDateTime startDate, LocalDateTime endDate, PageRequest pageRequest) {
        var offset = pageRequest.getPageNumber() * pageRequest.getPageSize();
        return statementRepository.findByAccountNumberAndDateRange(accountNumber,startDate,endDate, pageRequest.getPageSize(), offset);
    }

    @Override
    public Mono<Long> countByAccountNumberAndDateRange(String accountNumber, LocalDateTime startDate, LocalDateTime endDate) {
        return statementRepository.countByAccountNumberAndDateRange( accountNumber, startDate,endDate);
    }
}
