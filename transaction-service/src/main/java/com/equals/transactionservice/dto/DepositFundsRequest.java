package com.equals.transactionservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositFundsRequest
{
    private String accountNumber;

    private BigDecimal amount;

    private String name;

}
