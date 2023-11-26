package com.equals.customersupportservice.repository;

import com.equals.customersupportservice.domain.SupportTicket;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SupportTicketRepository extends ReactiveMongoRepository<SupportTicket, String> {

}
