package com.equals.transactionservice.client;

import com.equals.transactionservice.dto.BankAccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AccountServiceClient {
    private final WebClient webClient;
    public  AccountServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<BankAccountDto> getAccountBalance(String accountNumber) {
                return webClient.get().uri("http://localhost:8089/bank-account/" + accountNumber).retrieve()
                        .bodyToMono(BankAccountDto.class);


    }

}
