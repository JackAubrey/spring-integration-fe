package com.pluralsight.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.messaging.MessageChannel;

@Configuration
public class RabbitMQConfig {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    public static final String RABBIT_CHANNEL = "toRabbit";
    public static final String RABBIT_ROUTING_KEY = "globomantics.registrationRequest";
    public static final String REGISTRATION_REQUEST = "registrationRequest";
    

    @Bean
    public MessageChannel registrationRequest() {
        return MessageChannels
            .direct(REGISTRATION_REQUEST)
            .getObject();
    }

    @Bean
    public MessageChannel rabbitChannel() {
        return MessageChannels
            .direct(RABBIT_CHANNEL)
            .getObject();
    }

    @Bean
    public IntegrationFlow outboundChannelAdapter(AmqpOutboundEndpoint amqpOutboundEndpoint) {
        return IntegrationFlow
                .from(REGISTRATION_REQUEST)
                .log("Pre json:")
                .transform(Transformers.toJson())
                .log(Level.DEBUG)
                //.channel(RabbitMQConfig.RABBIT_CHANNEL)
                .handle(amqpOutboundEndpoint)
                .get();

    }

    @Bean    
    //@ServiceActivator(inputChannel = RABBIT_CHANNEL)
    public AmqpOutboundEndpoint amqpOutbound(RabbitTemplate rabbitTemplate) {
        logger.debug("Configure rabbit: {}", RABBIT_CHANNEL);

        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(rabbitTemplate);
        outbound.setRoutingKey(RABBIT_ROUTING_KEY);
        return outbound;
    }

}