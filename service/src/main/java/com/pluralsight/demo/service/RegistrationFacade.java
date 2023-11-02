package com.pluralsight.demo.service;

import java.time.OffsetDateTime;

import com.pluralsight.demo.model.AttendeeRegistration;

public interface RegistrationFacade {

    void register(OffsetDateTime dateTime, AttendeeRegistration registration);

}