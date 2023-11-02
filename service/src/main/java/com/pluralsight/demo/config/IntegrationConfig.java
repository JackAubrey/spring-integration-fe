package com.pluralsight.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Transformers;
import org.springframework.messaging.MessageChannel;

@Configuration
public class IntegrationConfig {

    public static String REGISTRATION_REQUEST = "registrationRequest";

    @Bean
    public MessageChannel registrationRequest() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow outboundChannelAdapter() {
        return IntegrationFlow
                .from(REGISTRATION_REQUEST)
                .log()
                .transform(Transformers.toJson())
                .channel("toRabbit")
                .log()
                .get();

    }

    @Bean
    public MessageChannel fromRabbit() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow inboundChannelAdapter() {
        return IntegrationFlow
                .from("fromRabbit")
                .transform(Transformers.fromJson())
                .channel("registrationRequest")
                .get();

    }

    @Bean
    public MessageChannel toRabbit() {
        return new QueueChannel();
    }

}