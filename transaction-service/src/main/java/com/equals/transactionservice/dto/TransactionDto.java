package com.equals.transactionservice.dto;

import com.equals.transactionservice.domain.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private Long id;
    private String transactionDate;
    private TransactionType transactionType;

    private String reference;

    private BigDecimal amount;

    private String fromAccount;

    private String toAccount;
}
