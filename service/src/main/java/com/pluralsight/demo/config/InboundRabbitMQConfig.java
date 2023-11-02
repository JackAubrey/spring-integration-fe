package com.pluralsight.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InboundRabbitMQConfig {
    private static final Logger logger = LoggerFactory.getLogger(InboundRabbitMQConfig.class);

    public static final String RABBIT_CHANNEL = "fromRabbit";
    public static final String RABBIT_ROUTING_KEY = "registrationRequest";
}