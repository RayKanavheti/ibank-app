package com.equals.accountservice.customer;

import com.equals.accountservice.auth.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "customers")
public class Customer {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    @Column("user_id")
    private Long userId;
    @Transient
    private User user;

}
