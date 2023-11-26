package com.equals.accountservice.service;

import com.equals.accountservice.domain.BankAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BankAccountCacheService {

    private final ReactiveRedisTemplate<String, BankAccount> redisTemplate;

    public BankAccountCacheService(ReactiveRedisTemplate<String, BankAccount> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public Mono<BankAccount> findByAccountNumber(String accountNumber) {
        log.info("============Find from Cache==============");
        log.info("Account Number: {}", accountNumber);
        return redisTemplate.opsForValue().get(accountNumber).flatMap(res -> {
            log.info("Found from Cache: {}", res);
            return Mono.just(res);
        });
    }

    public Mono<Boolean> saveToCache(String accountNumber, BankAccount bankAccount) {
        log.info("============Save To Cache==============");
        log.info("Account Number: {}", accountNumber);
        return redisTemplate.opsForValue().set(accountNumber, bankAccount).flatMap(res -> {
            log.info("Saved: {}", res);
            return Mono.just(res);
        });
    }

    public  Mono<Boolean> deleteFromCache(String accountNumber) {
        log.info("============Delete from Cache==============");
        log.info("Account Number: {}", accountNumber);
        return redisTemplate.opsForValue().delete(accountNumber).flatMap(res -> {
            log.info("Deleted: {}", res);
            return Mono.just(res);
        });

    }

}
