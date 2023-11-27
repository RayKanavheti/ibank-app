package com.equals.transactionservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("transactions")
public class Transaction implements Serializable {
    @Id
    private Long id;
    private LocalDateTime transactionDate;
    private TransactionType transactionType;

    private String reference;

    private BigDecimal amount;

    private String fromAccount;

    private String toAccount;

}
