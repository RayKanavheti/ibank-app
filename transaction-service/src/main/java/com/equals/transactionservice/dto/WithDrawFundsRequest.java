package com.equals.transactionservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithDrawFundsRequest {
    private String fromAccountNumber;
    private BigDecimal amount;
}
