package com.equals.accountservice.service;

import com.equals.accountservice.repository.BankAccountRepository;
import com.equals.accountservice.domain.BankAccount;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountServiceImplTest {
    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Test
    public void testCreateBankAccount() {
        // Given
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber("743433"); // Set any necessary fields for your bank account

        // Mock the repository method to return the saved bank account
        Mockito.when(bankAccountRepository.save(bankAccount))
                .thenReturn(Mono.just(bankAccount));

        // When
        BankAccount result = bankAccountService.createBankAccount(bankAccount).block(); // Use block to get the result

        // Then
        // Perform assertions on the result
        // For example:
        assertNotNull(result);
        assertEquals("743433", result.getAccountNumber());
    }

    @Test
    void addAccountBalance() {
        BigDecimal amount = BigDecimal.TEN;
        String accountNumber = "123456";

        // Mock the repository method to return a value
        Mockito.when(bankAccountRepository.addBankAccountBalance(amount, accountNumber))
                .thenReturn(Mono.just(1));

        // When
        Mono<Integer> result = bankAccountService.addAccountBalance(amount, accountNumber);

        // Then
        StepVerifier.create(result)
                .expectNext(1) // Expect the value returned by the repository method
                .verifyComplete();
    }

    @Test
    void subtractAccountBalance() {
        // Given
        BigDecimal amount = BigDecimal.TEN;
        String accountNumber = "123456";

        // Mock the repository method to return a value
        Mockito.when(bankAccountRepository.subtractBankAccountBalance(amount, accountNumber))
                .thenReturn(Mono.just(1));

        // When
        Mono<Integer> result = bankAccountService.subtractAccountBalance(amount, accountNumber);

        // Then
        StepVerifier.create(result)
                .expectNext(1) // Expect the value returned by the repository method
                .verifyComplete();
    }
}