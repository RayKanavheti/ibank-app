package com.equals.accountservice.api;

import com.equals.accountservice.domain.BankAccount;
import com.equals.accountservice.domain.Statement;
import com.equals.accountservice.dto.Paging;
import com.equals.accountservice.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/account/statement")
public class StatementController {

    @Autowired
    StatementService statementService;

    @PostMapping("/{accountNumber}")
    public Flux<Statement> getStatementByAccountNumber(@PathVariable String accountNumber) {
        return statementService.getStatementByAccountNumber(accountNumber);
    }

    @PostMapping("/list/{accountNumber}")
    public Mono<ServerResponse> getStatementByAccountNumberAndDateRangeAndPaging(@PathVariable String accountNumber,
                                                            @RequestParam(required = false) LocalDateTime startDate,
                                                            @RequestParam(required = false) LocalDateTime endDate,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Mono<Long> totalRecordsMono = statementService.countByAccountNumberAndDateRange(accountNumber, startDate, endDate);

        Mono<Paging.PagingResponse> resultMono = statementService.getStatementByAccountNumberAndDateRange(accountNumber, startDate, endDate, pageRequest)
                .collectList()
                .zipWith(totalRecordsMono, (statements, totalRecords) ->
                        new Paging.PagingResponse(statements, pageRequest.getPageNumber(), pageRequest.getPageSize(), totalRecords));
        return resultMono.flatMap(pagingResponse ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(pagingResponse));
    }
}
