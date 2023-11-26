package com.equals.accountservice.api;

import com.equals.accountservice.domain.BankAccount;
import com.equals.accountservice.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bank-account")
public class BankAccountController {

    @Autowired
    BankAccountService bankAccountService;

    @PostMapping()
    public Mono<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount) {
        return bankAccountService.createBankAccount(bankAccount);
    }

    @GetMapping("/{accountNumber}")
    public Mono<BankAccount> findByAccountNumber(@PathVariable String accountNumber) {
        return bankAccountService.findBankAccountByAccountNumber(accountNumber);
    }
}
