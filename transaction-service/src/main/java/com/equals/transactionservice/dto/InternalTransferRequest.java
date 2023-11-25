package com.equals.transactionservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InternalTransferRequest {
    private String toAccountNumber;
    private String fromAccountNumber;
    private BigDecimal amount;
}
