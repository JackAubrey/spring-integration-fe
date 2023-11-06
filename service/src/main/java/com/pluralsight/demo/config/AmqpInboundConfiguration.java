package com.pluralsight.demo.config;

import com.pluralsight.demo.handlers.RegistrationErrorHandler;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AmqpInboundConfiguration {
    @Bean("amqp-inbound-adapter")
    public AmqpInboundChannelAdapter inboundAdapter(ConnectionFactory connectionFactory,
                                                    @Qualifier("my-queue") Queue queue,
                                                    PlatformTransactionManager transactionManager,
                                                    RegistrationErrorHandler errorHandler) {

        return Amqp.inboundAdapter(connectionFactory, queue)
                .configureContainer( (spec) -> {
//                    spec.acknowledgeMode(AcknowledgeMode.AUTO);
//                    spec.channelTransacted(true);
//                    spec.transactionManager(transactionManager);
//                    spec.defaultRequeueRejected(true);
                    System.out.println("Mi Spe config");
                    spec.errorHandler(errorHandler);
                })
                .messageConverter(new Jackson2JsonMessageConverter())
                .autoStartup(true)
                .getObject();
    }

    @Bean("my-queue")
    public Queue queue() {
        return QueueBuilder.durable("danilo.queue")
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", "danilo.dlq")
                .build();
    }

    @Bean("my-dlq")
    public Queue dlq() {
        return new Queue("danilo.dlq");
    }
}
