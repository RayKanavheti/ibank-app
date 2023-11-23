package com.equals.transactionservice.client;

import com.equals.transactionservice.config.WebClientConfig;
import com.equals.transactionservice.dto.AccountBalanceDto;
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

    public AccountBalanceDto getAccountBalance(String accountNumber) {
                return  webClient.get().uri("http://ACCOUNT-SERVICE/account/balance/" + accountNumber).retrieve()
                        .bodyToMono(AccountBalanceDto.class).block();


    }

}
