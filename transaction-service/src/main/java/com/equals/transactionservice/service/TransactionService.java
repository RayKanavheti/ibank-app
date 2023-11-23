package com.equals.transactionservice.service;

import com.equals.transactionservice.domain.Transaction;
import com.equals.transactionservice.dto.DepositFundsRequest;
import reactor.core.publisher.Mono;

public interface TransactionService {



    Mono<Transaction> depositFunds(DepositFundsRequest request);

    void internalTransfer();

    void withdrawFunds();

    void externalTransfer();

    void billPayment();


    void balanceEnquiry();
}
