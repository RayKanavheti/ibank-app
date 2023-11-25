package com.equals.transactionservice.api;

import com.equals.transactionservice.domain.Transaction;
import com.equals.transactionservice.dto.DepositFundsRequest;
import com.equals.transactionservice.dto.InternalTransferRequest;
import com.equals.transactionservice.dto.WithDrawFundsRequest;
import com.equals.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/deposit")
    public Mono<Transaction> depositFunds(@RequestBody DepositFundsRequest request) {
        return transactionService.depositFunds(request);
    }
    @PostMapping("/internal")
    public Mono<Transaction> internalFundsTransfer(@RequestBody InternalTransferRequest request) {
        return transactionService.internalTransfer(request);
    }

    @PostMapping("/withdraw")
    public Mono<Transaction> cashWithDraw(@RequestBody WithDrawFundsRequest request) {
        return transactionService.withdrawFunds(request);
    }
}
