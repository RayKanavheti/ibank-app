package com.equals.accountservice.customer;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerRepository  extends ReactiveCrudRepository<Customer, Long> {
}
