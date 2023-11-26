package com.equals.accountservice.service;

import com.equals.accountservice.domain.BankAccount;
import com.equals.accountservice.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountCacheService bankAccountCacheService;

    @Override
    public Mono<BankAccount> createBankAccount(BankAccount bankAccount) {
        bankAccount.setAccountNumber(generateRandomNumber());
        bankAccount.setAccountBalance(BigDecimal.ZERO);
        return bankAccountRepository.save(bankAccount);
    }


    @Override
    public Mono<Integer> addAccountBalance(BigDecimal amount, String accountNumber) {
        return bankAccountRepository.addBankAccountBalance(amount, accountNumber).flatMap(val -> bankAccountCacheService.deleteFromCache(accountNumber)
                .thenReturn(val));
    }

    @Override
    public Mono<Integer> subtractAccountBalance(BigDecimal amount, String accountNumber) {

        return bankAccountRepository.subtractBankAccountBalance(amount, accountNumber)
                .flatMap(val -> bankAccountCacheService.deleteFromCache(accountNumber)
                        .thenReturn(val));
    }

    @Override
    public Mono<BankAccount> findBankAccountByAccountNumber(String accountNumber) {
        return bankAccountCacheService.findByAccountNumber(accountNumber)
                .switchIfEmpty(bankAccountRepository.findBankAccountByAccountNumber(accountNumber)
                        .flatMap(bankAccount -> bankAccountCacheService.saveToCache(bankAccount.getAccountNumber(), bankAccount)
                                .thenReturn(bankAccount))
                );

    }

    @Override
    public Flux<BankAccount> findByBankAccountNumbers(List<String> accountNumbers) {
        return bankAccountRepository.findByAccountNumbers(accountNumbers);
    }

    private String generateRandomNumber() {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        var length = 10;

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }
}
