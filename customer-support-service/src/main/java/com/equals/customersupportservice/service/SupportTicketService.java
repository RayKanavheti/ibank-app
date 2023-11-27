package com.equals.customersupportservice.service;

import com.equals.customersupportservice.domain.SupportTicket;
import com.equals.customersupportservice.repository.SupportTicketRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;



@Service
public class SupportTicketService {

    @Autowired
     SupportTicketRepository supportTicketRepository;
    @Autowired
     StreamBridge streamBridge;
    public static final String TICKET_QUEUE = "supportTicketBinding-out-0";

    public Mono<SupportTicket> createSupportTicket(SupportTicket supportTicket) {

      return supportTicketRepository.save(supportTicket).flatMap(s -> {
            streamBridge.send(TICKET_QUEUE, s);
            return Mono.just(s);
        });

    }
}
