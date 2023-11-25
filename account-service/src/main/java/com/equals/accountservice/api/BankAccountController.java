package com.equals.accountservice.api;

import com.equals.accountservice.domain.BankAccount;
import com.equals.accountservice.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
