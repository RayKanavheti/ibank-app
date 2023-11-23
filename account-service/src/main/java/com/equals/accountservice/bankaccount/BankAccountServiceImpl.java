package com.equals.accountservice.bankaccount;

import com.equals.accountservice.customer.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    @Override
    public Mono<BankAccount> createBankAccount(BankAccount bankAccount) {
        bankAccount.setAccountNumber(generateRandomNumber());
        bankAccount.setAccountBalance(BigDecimal.ZERO);
        return bankAccountRepository.save(bankAccount);
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
