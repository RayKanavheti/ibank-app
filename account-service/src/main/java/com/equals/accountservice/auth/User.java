package com.equals.accountservice.auth;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "users")
public class User {
    @Id
    private Long id;
    private String username;
    private String email;
    private String password;
}