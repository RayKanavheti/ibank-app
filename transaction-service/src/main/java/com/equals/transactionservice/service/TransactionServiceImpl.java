package com.equals.transactionservice.service;

import com.equals.transactionservice.client.AccountServiceClient;
import com.equals.transactionservice.common.NotFoundException;
import com.equals.transactionservice.domain.Transaction;
import com.equals.transactionservice.domain.TransactionType;
import com.equals.transactionservice.dto.DepositFundsRequest;
import com.equals.transactionservice.dto.InternalTransferRequest;
import com.equals.transactionservice.dto.TransactionDto;
import com.equals.transactionservice.dto.WithDrawFundsRequest;
import com.equals.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.redis.connection.Limit;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import java.util.UUID;


//import static com.equals.transactionservice.config.RabbitConfig.IBANK_TRANSACTION_QUEUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final RabbitTemplate rabbitTemplate;
    private final StreamBridge streamBridge;
    private final AccountServiceClient accountServiceClient;
    private final ReactiveRedisTemplate<String, Transaction> reactiveRedisTemplate;
    public static final String IBANK_TRANSACTION_QUEUE = "transactionBinding-out-0";

    @Override
    @Transactional
    public Mono<Transaction> depositFunds(DepositFundsRequest request) {
        return accountServiceClient.getAccountBalance(request.getToAccountNumber()).flatMap(acc -> {
            Transaction transaction = buildTransaction(TransactionType.DEPOSIT, request.getAmount());
            transaction.setToAccount(request.getToAccountNumber());

            return transactionRepository.save(transaction).
                    flatMap(trans -> {
                        log.info("Transaction {}", trans);
                        TransactionDto transactionDto = buildTransactionDto(trans);
                        transactionDto.setToAccount(trans.getToAccount());
                        // rabbitTemplate.convertAndSend(IBANK_TRANSACTION_QUEUE, transactionDto);
                        streamBridge.send(IBANK_TRANSACTION_QUEUE, transactionDto);

                        return Mono.just(trans);
                    }).doOnError(throwable -> log.error("Failed to Deposit Funds", throwable));
        }).switchIfEmpty(Mono.error(new NotFoundException("Account Not found")));


    }

    @Override
    @Transactional
    public Mono<Transaction> internalTransfer(InternalTransferRequest request) {
        Transaction transaction = buildTransaction(TransactionType.INTERNAL_FUND_TRANSFER, request.getAmount());
        transaction.setToAccount(request.getToAccountNumber());
        transaction.setFromAccount(request.getFromAccountNumber());
        return transactionRepository.save(transaction).
                flatMap(trans -> {
                    log.info("Transaction {}", trans);
                    TransactionDto transactionDto = buildTransactionDto(trans);
                    transactionDto.setFromAccount(trans.getFromAccount());
                    transactionDto.setToAccount(trans.getToAccount());

                    // rabbitTemplate.convertAndSend(IBANK_TRANSACTION_QUEUE, transactionDto);
                    streamBridge.send(IBANK_TRANSACTION_QUEUE, transactionDto);
                    return Mono.just(trans);
                }).doOnError(throwable -> log.error("Failed to process internal Transfer", throwable));
    }

    @Override
    @Transactional
    public Mono<Transaction> withdrawFunds(WithDrawFundsRequest request) {
        Transaction transaction = buildTransaction(TransactionType.WITHDRAWAL, request.getAmount());
        transaction.setFromAccount(request.getFromAccountNumber());
        return transactionRepository.save(transaction).
                flatMap(trans -> {
                    log.info("Transaction {}", trans);
                    TransactionDto transactionDto = buildTransactionDto(trans);
                    transactionDto.setFromAccount(trans.getFromAccount());
                    // rabbitTemplate.convertAndSend(IBANK_TRANSACTION_QUEUE, transactionDto);
                    streamBridge.send(IBANK_TRANSACTION_QUEUE, transactionDto);

                    return Mono.just(trans);
                }).doOnError(throwable -> log.error("Failed to withdraw funds", throwable));
    }

    @Override
    public Mono<Transaction> externalTransfer(InternalTransferRequest request) {

        return internalTransfer(request);
        // TODO to include logic for external banks e.g external bank acknowledging having received funds by way of a call back URL, probably stub a third party bank
    }

    @Override
    public Flux<Transaction> getRecentTransactions(String accountNumber) {
        String key = "recentTransactions-" + accountNumber;
        Limit limit = Limit.limit().count(5);
        var scoreRange = org.springframework.data.domain.Range.closed(100.0, Double.POSITIVE_INFINITY);
        return reactiveRedisTemplate.opsForZSet()
                .reverseRangeByScore(key, scoreRange, limit)
                .flatMap(transactionId -> reactiveRedisTemplate.opsForValue().get(transactionId))
                .collectList()
                .flatMapMany(transactions -> {
                    if (transactions.isEmpty()) {
                        return transactionRepository.findTop5ByFromAccountOrToAccountOrderByTransactionDateDesc(accountNumber, accountNumber)
                                .doOnNext(transaction -> reactiveRedisTemplate.opsForZSet()
                                        .add(key, transaction,
                                                transaction.getTransactionDate().toEpochSecond(ZoneOffset.UTC))
                                        .then()
                                        .subscribe())
                                .timeout(Duration.ofSeconds(5));
                    } else {
                        return Flux.fromIterable(transactions);
                    }
                });

        // TODO giving me a serializable error, needs to be fixed later
    }


    private Transaction buildTransaction(TransactionType type, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(type);
        transaction.setReference(UUID.randomUUID().toString());
        transaction.setAmount(amount);
        return transaction;
    }

    private TransactionDto buildTransactionDto(Transaction trans) {
        TransactionDto transaction = new TransactionDto();
        transaction.setTransactionType(trans.getTransactionType());
        transaction.setTransactionDate(trans.getTransactionDate().format(formatDate()));
        transaction.setId(trans.getId());
        transaction.setReference(trans.getReference());
        transaction.setAmount(trans.getAmount());
        return transaction;
    }

    DateTimeFormatter formatDate() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    }


}
