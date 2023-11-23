package com.equals.transactionservice.service;

import com.equals.transactionservice.client.AccountServiceClient;
import com.equals.transactionservice.domain.Statement;
import com.equals.transactionservice.domain.Transaction;
import com.equals.transactionservice.domain.TransactionType;
import com.equals.transactionservice.dto.AccountBalanceDto;
import com.equals.transactionservice.dto.DepositFundsRequest;
import com.equals.transactionservice.repository.StatementRepository;
import com.equals.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.equals.transactionservice.config.RabbitConfig.IBANK_TRANSACTION_QUEUE;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final StatementRepository statementRepository;

    private final RabbitTemplate rabbitTemplate;
    private final AccountServiceClient accountServiceClient;

    @Override
    @Transactional
    public Mono<Transaction> depositFunds(DepositFundsRequest request) {

        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setAmount(request.getAmount());
        transaction.setReference(UUID.randomUUID().toString());
        transaction.setToAccount(request.getAccountNumber());

        return transactionRepository.save(transaction).
                flatMap(trans -> {
                    rabbitTemplate.convertAndSend(IBANK_TRANSACTION_QUEUE, trans);
                    statementRepository.getRecentBalance(trans.getTransactionDate(), request.getAccountNumber()).subscribe(
                            balance -> {
                                Statement statement = new Statement();
                                if (balance != null) {

                                    statement.setBalance(balance);
                                } else{
                                    AccountBalanceDto balanceDto = accountServiceClient.getAccountBalance(request.getAccountNumber());
                                    statement.setBalance(balanceDto.getBalance());
                                }
                                statement.setTransactionId(trans.getId());
                                statement.setCreditAmount(trans.getAmount());
                                statement.setNarrative(trans.getTransactionType().toString());
                                statement.setReference(trans.getReference());
                                statement.setAccountNumber(request.getAccountNumber());
                                statement.setPostDate(trans.getTransactionDate());
                                statementRepository.save(statement).block();

                            }
                    );
                    return Mono.just(trans);
                });

    }

    @Override
    public void internalTransfer() {

    }

    @Override
    public void withdrawFunds() {

    }

    @Override
    public void externalTransfer() {

    }

    @Override
    public void billPayment() {

    }

    @Override
    public void balanceEnquiry() {

    }
}
