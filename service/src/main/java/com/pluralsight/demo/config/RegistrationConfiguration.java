package com.pluralsight.demo.config;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.amqp.dsl.AmqpOutboundChannelAdapterSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class RegistrationConfiguration {
    private static final Logger logger = getLogger(RegistrationConfiguration.class);
    public static final String RABBIT_CHANNEL = "toRabbit";
    public static final String REGISTRATION_REQUEST = "registrationRequest";
    public static final String RABBIT_ROUTING_KEY = "danilo.queue";

    @Bean("registration-request-channel")
    public MessageChannel registrationRequest() {
        return MessageChannels
                .direct(REGISTRATION_REQUEST)
                .getObject();
    }

    @Bean("amqp-outbound-adapter")
    public AmqpOutboundChannelAdapterSpec amqpOutbound(RabbitTemplate rabbitTemplate) {
        logger.debug("Configure rabbit: {}", RABBIT_CHANNEL);

        return Amqp.outboundAdapter(rabbitTemplate).routingKey(RABBIT_ROUTING_KEY);
    }
}
