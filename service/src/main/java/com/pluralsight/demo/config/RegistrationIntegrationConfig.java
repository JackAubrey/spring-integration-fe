package com.pluralsight.demo.config;

import com.pluralsight.demo.model.AttendeeRegistration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.messaging.MessageChannel;

@Configuration
public class RegistrationIntegrationConfig {
    public static final String REGISTRATION_CHANNEL = "registrationRequestChannel";

    @Bean(REGISTRATION_CHANNEL)
    public MessageChannel registrationRequest() {
        return MessageChannels.direct(REGISTRATION_CHANNEL).getObject();
    }

    @Transformer(inputChannel = REGISTRATION_CHANNEL, outputChannel = RabbitIntegrationConfig.RABBIT_CHANNEL)
    public JsonToObjectTransformer exampleTransformer(AttendeeRegistration payload) {
        return new JsonToObjectTransformer(AttendeeRegistration.class);
    }
}
