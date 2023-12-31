package com.equals.accountservice.repository;

import com.equals.accountservice.domain.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerRepository  extends ReactiveCrudRepository<Customer, Long> {
}
