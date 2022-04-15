package com.example.casework2;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Exchange orderExchange(){
        return new DirectExchange("kitchen");
    }

    @Bean
    public Queue orderQueue(){
        return new Queue("kitchen.orders", false);
    }
}
