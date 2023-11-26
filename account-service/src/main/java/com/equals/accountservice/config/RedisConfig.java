package com.equals.accountservice.config;

import com.equals.accountservice.domain.BankAccount;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration
@EnableRedisRepositories
public class RedisConfig  {
//    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofHours(12))
//                 .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
//
//
//        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
//                .cacheDefaults(cacheConfiguration)
//                .build();
//    }

    @Bean
    public ReactiveRedisTemplate<String, BankAccount> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory connectionFactory) {
        Jackson2JsonRedisSerializer<BankAccount> serializer =
                new Jackson2JsonRedisSerializer<>(BankAccount.class);

        RedisSerializationContext<String, BankAccount> serializationContext =
                RedisSerializationContext.<String, BankAccount>newSerializationContext(RedisSerializer.string())
                        .value(serializer)
                        .build();

        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }
//    @Bean
//    public ReactiveStringRedisTemplate reactiveStringRedisTemplate(
//            ReactiveRedisConnectionFactory connectionFactory) {
//        return new ReactiveStringRedisTemplate(connectionFactory);
//    }
}
