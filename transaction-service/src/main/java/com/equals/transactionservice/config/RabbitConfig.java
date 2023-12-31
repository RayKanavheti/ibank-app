package com.equals.transactionservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
//@EnableRabbit
//public class RabbitConfig {
//
//
//    public static final String exchange = "ibank-transaction-service-exchange";
//
//    public static final String routingKey = "ibank-transaction-service-key";
//
//    public static final String IBANK_TRANSACTION_QUEUE = "ibank-transaction-service-queue";
//
//    @Bean
//    public Queue queue() {
//        return new Queue(IBANK_TRANSACTION_QUEUE);
//    }
//
//    @Bean
//    public TopicExchange topicExchange() {
//        return new TopicExchange(exchange);
//    }
//
//    @Bean
//    public Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
//    }
//
//    @Bean
//    public MessageConverter converter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    public AmqpTemplate template(ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(converter());
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        return rabbitTemplate;
//    }
//
//
//}
