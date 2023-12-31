package com.equals.transactionservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositFundsRequest
{
    private String toAccountNumber;

    private BigDecimal amount;

}
