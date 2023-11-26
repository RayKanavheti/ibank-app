package com.equals.customersupportservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("tickets")
public class SupportTicket {
    @Id
    private String id;

    private String customerId;

    private String issue;

    private LocalDateTime createdAt;
}
