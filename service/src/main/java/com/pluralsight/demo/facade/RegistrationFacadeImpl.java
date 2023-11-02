package com.pluralsight.demo.facade;

import com.pluralsight.demo.config.RegistrationIntegrationConfig;
import com.pluralsight.demo.model.AttendeeRegistration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class RegistrationFacadeImpl implements RegistrationFacade {
    private final MessageChannel registrationRequestChannel;

    public RegistrationFacadeImpl(@Qualifier(RegistrationIntegrationConfig.REGISTRATION_CHANNEL) MessageChannel registrationRequestChannel) {
        this.registrationRequestChannel = registrationRequestChannel;
    }

    @Override
    public void register(OffsetDateTime dateTime, AttendeeRegistration registration) {
        Message<AttendeeRegistration> message = MessageBuilder.withPayload(registration)
                .setHeader("dateTime", OffsetDateTime.now())
                .build();

        registrationRequestChannel.send(message);
    }
}
