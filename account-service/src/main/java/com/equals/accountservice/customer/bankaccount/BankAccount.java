package com.equals.accountservice.customer.bankaccount;

import com.equals.accountservice.customer.Customer;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("bank_account")
public class BankAccount {
    @Id
    private Long id;
    private Long accountNumber;

    private BigDecimal accountBalance;

    private BankAccountType accountType; //Enumeration

    @Column("customer_id")
    private Long customerId;
    @Transient
    private Customer customer;
}
