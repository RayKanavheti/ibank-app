package com.equals.accountservice.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

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

    private String ToAccount;
}
