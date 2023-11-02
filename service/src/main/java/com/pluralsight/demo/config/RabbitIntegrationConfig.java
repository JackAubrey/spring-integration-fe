package com.pluralsight.demo.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
public class RabbitIntegrationConfig {
    public static final String RABBIT_CHANNEL = "toRabbit";
    public static final String RABBIT_ROUTING_KEY = "globomantics.registrationRequest";

    @Bean
    public MessageChannel rabbitChannel() {
        return MessageChannels.direct(RABBIT_CHANNEL).getObject();
    }

    @Bean
    @ServiceActivator(inputChannel = RABBIT_CHANNEL)
    public AmqpOutboundEndpoint amqpOutbound(RabbitTemplate rabbitTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(rabbitTemplate);
        outbound.setRoutingKey(RABBIT_ROUTING_KEY);
        return outbound;
    }
}
