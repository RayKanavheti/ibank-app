package com.equals.accountservice.bankaccount;

import com.equals.accountservice.customer.Customer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Table("bank_account")
public class BankAccount {
    @Id
    private Long id;
    private String accountNumber;

    private BigDecimal accountBalance;

    private BankAccountType accountType; //Enumeration

    @Column("customer_id")
    private Long customerId;
    @Transient
    private Customer customer;
}
