package com.equals.transactionservice.service;

import com.equals.transactionservice.domain.Transaction;
import com.equals.transactionservice.dto.DepositFundsRequest;
import com.equals.transactionservice.dto.InternalTransferRequest;
import com.equals.transactionservice.dto.WithDrawFundsRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {



    Mono<Transaction> depositFunds(DepositFundsRequest request);

    Mono<Transaction> internalTransfer(InternalTransferRequest request);

     Mono<Transaction>  withdrawFunds(WithDrawFundsRequest request);

    Mono<Transaction> externalTransfer(InternalTransferRequest request);

    Flux<Transaction> getRecentTransactions(String accountNumber);




}
