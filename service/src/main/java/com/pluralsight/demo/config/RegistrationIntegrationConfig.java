package com.pluralsight.demo.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.messaging.MessageChannel;

@Configuration
public class RegistrationIntegrationConfig {
    public static final String REGISTRATION_CHANNEL = "registrationRequestChannel";

    @Bean(REGISTRATION_CHANNEL)
    public MessageChannel registrationRequest() {
        return MessageChannels.direct(REGISTRATION_CHANNEL).getObject();
    }

    @Bean
    @Transformer(inputChannel = REGISTRATION_CHANNEL, outputChannel = RabbitIntegrationConfig.RABBIT_CHANNEL)
    public ObjectToJsonTransformer registrationToJson(Jackson2JsonObjectMapper mapper) {
        System.out.println(">>> 2 . configuring transformer");
        return new ObjectToJsonTransformer(mapper);
    }

    @Bean
    public Jackson2JsonObjectMapper converter(){
        System.out.println(">>> 1 . configuring message converter");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        return new Jackson2JsonObjectMapper(mapper);
    }
}
