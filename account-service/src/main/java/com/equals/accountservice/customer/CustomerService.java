package com.equals.accountservice.customer;

import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<Customer> createCustomer(Customer customer);
}
