package com.pluralsight.demo.config;

import com.pluralsight.demo.model.AttendeeRegistration;
import com.pluralsight.demo.service.RegistrationService;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.transformer.Transformer;

@Configuration
public class AmqpInboundIntegration {
    private final RegistrationService registrationFacade;

    public AmqpInboundIntegration(RegistrationService registrationFacade) {
        this.registrationFacade = registrationFacade;
    }

    @Bean
    public IntegrationFlow amqpInbound(
            @Qualifier("amqp-inbound-adapter")AmqpInboundChannelAdapter inboundChannelAdapter
            ) {
        return IntegrationFlow.from(inboundChannelAdapter)
                .log("amqpInbound.start-process")
                .log( m -> {
                    System.out.println("Message Headers = "+m.getHeaders());
                    System.out.println("Message Payload = "+m.getPayload());
                    return m;
                })
                .transform(Transformers.fromJson(AttendeeRegistration.class))
                .handle(registrationFacade, "register")
                .get();
    }
}
