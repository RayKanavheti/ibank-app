package com.equals.transactionservice.config;


import com.equals.transactionservice.domain.Transaction;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {


    @Bean
    public ReactiveRedisTemplate<String, Transaction> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory connectionFactory) {
        Jackson2JsonRedisSerializer<Transaction> serializer =
                new Jackson2JsonRedisSerializer<>(Transaction.class);

        RedisSerializationContext<String, Transaction> serializationContext =
                RedisSerializationContext.<String, Transaction>newSerializationContext(RedisSerializer.string())
                        .value(serializer)
                        .build();

        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

}
