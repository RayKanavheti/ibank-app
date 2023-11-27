package com.equals.transactionservice.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;

@Data
public class BankAccountDto {

    private Long id;
    private String accountNumber;
    private BigDecimal accountBalance;
    private String accountType; //Enumeration
    private Long customerId;

}
