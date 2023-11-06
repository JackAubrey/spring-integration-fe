package com.pluralsight.demo.handlers;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component
public class RegistrationErrorHandler implements ErrorHandler {
    /**
     * Handle the given error, possibly rethrowing it as a fatal exception.
     *
     * @param t
     */
    @Override
    public void handleError(Throwable t) {
        System.out.println("error handler");
        throw new AmqpRejectAndDontRequeueException("Registration error:", t);
    }
}
