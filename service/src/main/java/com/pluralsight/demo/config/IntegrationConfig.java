package com.pluralsight.demo.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.messaging.MessageChannel;

import com.pluralsight.demo.model.AttendeeRegistration;
import com.pluralsight.demo.service.RegistrationService;

@Configuration
public class IntegrationConfig {

    public static final String FROM_RABBIT_CHANNEL = "fromRabbit";
    public static final String IN_REGISTRATION_REQUEST = "inRegistrationRequest";

    // @Bean
    // public IntegrationFlow inboundChannelAdapter() {
    // return IntegrationFlow
    // .from("fromRabbit")
    // .transform(Transformers.fromJson())
    // .channel("registrationRequest")
    // .get();

    // }

    @Bean
    public AbstractMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        messageListenerContainer.setQueueNames(RabbitMQConfig.RABBIT_ROUTING_KEY);
        return messageListenerContainer;
    }

    @Bean
    public AmqpInboundChannelAdapter inboundChannelAdapter(AbstractMessageListenerContainer messageListenerContainer) {
        AmqpInboundChannelAdapter adapter = new AmqpInboundChannelAdapter(messageListenerContainer);
        adapter.setOutputChannelName(FROM_RABBIT_CHANNEL);
        return adapter;
    }

    @Bean
    public MessageChannel fromRabbit() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow inboundFlow(RegistrationService registrationService) {
        return IntegrationFlow
                .from(FROM_RABBIT_CHANNEL)
                .log("From rabbit channel")
                .transform(Transformers.fromJson(AttendeeRegistration.class))
                .handle(registrationService, "register")
                .get();
    }

    @Bean
    public MessageChannel inRegistrationRequest() {
        return new DirectChannel();
    }

}