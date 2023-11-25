package com.equals.accountservice.service;

import com.equals.accountservice.domain.Customer;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<Customer> createCustomer(Customer customer);
}
