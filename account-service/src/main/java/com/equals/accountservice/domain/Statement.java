package com.equals.accountservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("statements")
public class Statement {
    @Id
    private Long id;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private BigDecimal balance;
    private String narrative;

    private Long transactionId;

    private String reference;

    private LocalDateTime postDate;

    private String accountNumber;


}
