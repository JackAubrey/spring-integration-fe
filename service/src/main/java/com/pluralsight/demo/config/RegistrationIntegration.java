package com.pluralsight.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.AmqpOutboundChannelAdapterSpec;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.MessageChannel;

@Configuration
public class RegistrationIntegration {
    @Bean
    public IntegrationFlow outboundChannelAdapter(
            @Qualifier("registration-request-channel") MessageChannel fromChannel,
            @Qualifier("amqp-outbound-adapter") AmqpOutboundChannelAdapterSpec amqpOutboundAdapter) {
        return IntegrationFlow
                .from(fromChannel)
                .log("Pre json:")
                .transform(Transformers.toJson())
                .log(LoggingHandler.Level.DEBUG)
                .handle(amqpOutboundAdapter)
                .get();

    }
}
