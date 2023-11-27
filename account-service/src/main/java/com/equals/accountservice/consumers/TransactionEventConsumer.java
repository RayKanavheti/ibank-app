package com.equals.accountservice.consumers;

import com.equals.accountservice.domain.Statement;
import com.equals.accountservice.dto.TransactionDto;
import com.equals.accountservice.service.BankAccountService;
import com.equals.accountservice.service.StatementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class TransactionEventConsumer {

    private final StatementService statementService;
    private final BankAccountService bankAccountService;

    @Bean
    public Consumer<TransactionDto> transactionBinding() {
        return (msg) -> {
            try {
                log.info("Received the message in Consumer: {}", msg);
                System.out.println("Received the message in Consumer :" + msg);
                receiveTransactions(msg).subscribe();
            } catch (Exception e) {
                log.error("Error processing message: {}", msg, e);
            }
        };
    }

    public Mono<Void> receiveTransactions(TransactionDto transaction) {

        switch (transaction.getTransactionType()) {
            case DEPOSIT -> {
                return processDeposit(transaction);

            }

            case INTERNAL_FUND_TRANSFER -> {
                return processInternalFundTransfer(transaction);

            }

            case WITHDRAWAL -> {

                return processWithDrawal(transaction);
            }


        }
        return Mono.empty();
    }


    public Mono<Void> processInternalFundTransfer(TransactionDto transaction) {

        List<String> accountNumbersToCheck = Arrays.asList(transaction.getFromAccount(), transaction.getToAccount());

        return bankAccountService.findByBankAccountNumbers(accountNumbersToCheck)
                .collectList()
                .flatMap(existingAccounts -> {
                    if (existingAccounts.size() == 2) {
                        Statement statement = buildStatement(transaction);
                        statement.setCreditAmount(transaction.getAmount());
                        statement.setDebitAmount(BigDecimal.ZERO);
                        statement.setAccountNumber(transaction.getToAccount());

                        Statement statement2 = buildStatement(transaction);
                        statement2.setDebitAmount(transaction.getAmount());
                        statement2.setCreditAmount(BigDecimal.ZERO);
                        statement2.setAccountNumber(transaction.getFromAccount());
                        existingAccounts.forEach(acc -> {
                            if (acc.getAccountNumber().equals(transaction.getToAccount())) {
                                statement.setBalance(acc.getAccountBalance().add(transaction.getAmount()));
                            } else {
                                statement2.setBalance(acc.getAccountBalance().subtract(transaction.getAmount()));
                            }
                        });
                        return Mono.zip(statementService.saveStatement(statement),
                                        statementService.saveStatement(statement2),
                                        bankAccountService.addAccountBalance(transaction.getAmount(), transaction.getToAccount()),
                                        bankAccountService.subtractAccountBalance(transaction.getAmount(), transaction.getFromAccount())
                                ).doOnSuccess(success -> log.info("Internal Funds transfer successfull"))
                                .doOnError(error -> log.error("Error processing deposit", error))
                                .then();

                    } else {
                        return Mono.error(new RuntimeException("Exactly two accounts are required, but found: " + existingAccounts.size()));
                    }
                }).doOnSuccess(success -> log.info("Internal Funds processing completed"))
                .doOnError(error -> log.error("Error processing internal Funds transfer", error))
                .then();


    }


    public Mono<Void> processDeposit(TransactionDto transaction) {
        return bankAccountService.findBankAccountByAccountNumber(transaction.getToAccount())
                .flatMap(account -> {
                    Statement statement = buildStatement(transaction);
                    statement.setCreditAmount(transaction.getAmount());
                    statement.setDebitAmount(BigDecimal.ZERO);
                    statement.setAccountNumber(transaction.getToAccount());
                    statement.setBalance(transaction.getAmount().add(account.getAccountBalance()));

                    return Mono.zip(
                                    statementService.saveStatement(statement),
                                    bankAccountService.addAccountBalance(transaction.getAmount(), transaction.getToAccount())
                            )
                            .doOnSuccess(success -> log.info("Deposit processed successfully"))
                            .doOnError(error -> log.error("Error processing deposit", error))
                            .then();
                })
                .doOnSuccess(success -> log.info("Deposit processing completed"))
                .doOnError(error -> log.error("Error processing deposit", error))
                .then();
    }

    public Mono<Void> processWithDrawal(TransactionDto transaction) {
        return bankAccountService.findBankAccountByAccountNumber(transaction.getFromAccount())
                .flatMap(account -> {
                    Statement statement = buildStatement(transaction);
                    statement.setCreditAmount(BigDecimal.ZERO);
                    statement.setDebitAmount(transaction.getAmount());
                    statement.setAccountNumber(transaction.getFromAccount());
                    statement.setBalance(account.getAccountBalance().subtract(transaction.getAmount()));

                    return Mono.zip(
                                    statementService.saveStatement(statement),
                                    bankAccountService.subtractAccountBalance(transaction.getAmount(), transaction.getFromAccount())
                            )
                            .doOnSuccess(success -> log.info("Cash withdrawal processed successfully"))
                            .doOnError(error -> log.error("Error processing withdrawal", error))
                            .then();
                })
                .doOnSuccess(success -> log.info("Withdrawal processing completed"))
                .doOnError(error -> log.error("Error processing cash withdrawal", error))
                .then();
    }

    private Statement buildStatement(TransactionDto transactionDto) {
        Statement statement = new Statement();
        statement.setTransactionId(transactionDto.getId());
        statement.setNarrative(transactionDto.getTransactionType().toString());
        statement.setReference(transactionDto.getReference());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(transactionDto.getTransactionDate(), formatter);
        statement.setPostDate(localDateTime);
        return statement;
    }
}
