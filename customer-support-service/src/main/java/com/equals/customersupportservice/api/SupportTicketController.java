package com.equals.customersupportservice.api;

import com.equals.customersupportservice.domain.SupportTicket;
import com.equals.customersupportservice.service.SupportTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/tickets")
public class SupportTicketController {

    @Autowired
    SupportTicketService supportTicketService;

    @PostMapping("/create")
    public Mono<SupportTicket> createTicket(@RequestBody SupportTicket request) {
        request.setCreatedAt(LocalDateTime.now());
        return supportTicketService.createSupportTicket(request);
    }

}
