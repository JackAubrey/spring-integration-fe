package com.pluralsight.demo.service;

import java.time.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.pluralsight.demo.config.RabbitMQConfig;
import com.pluralsight.demo.model.AttendeeRegistration;

@Service
public class RegistrationFacadeImpl implements RegistrationFacade {
    private static final Logger LOG = LoggerFactory.getLogger(RegistrationFacadeImpl.class);

    private final MessageChannel registrationRequestChannel;

    /**
     * @param registrationRequestChannel
     */
    public RegistrationFacadeImpl(@Qualifier(RabbitMQConfig.REGISTRATION_REQUEST) MessageChannel registrationRequestChannel) {
        this.registrationRequestChannel = registrationRequestChannel;
    }

    @Override
    public void register(OffsetDateTime dateTime, AttendeeRegistration registration) {
        Message<AttendeeRegistration> message = MessageBuilder
                .withPayload(registration)
                .setHeader("dateTime", OffsetDateTime.now())
                .build();

        registrationRequestChannel.send(message);
        LOG.debug("Message sent to registration request channel");
    }

}