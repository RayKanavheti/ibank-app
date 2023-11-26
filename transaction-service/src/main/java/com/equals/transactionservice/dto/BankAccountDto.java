package com.equals.transactionservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountDto {
    private BigDecimal balance;
    private String accountNumber;
}
