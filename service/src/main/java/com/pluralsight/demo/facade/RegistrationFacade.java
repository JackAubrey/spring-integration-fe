package com.pluralsight.demo.facade;

import com.pluralsight.demo.model.AttendeeRegistration;

import java.time.OffsetDateTime;

public interface RegistrationFacade {
    public void register(OffsetDateTime dateTime, AttendeeRegistration registration);
}
