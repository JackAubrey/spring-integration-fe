package com.pluralsight.demo.web;

import java.time.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pluralsight.demo.model.AttendeeRegistration;
import com.pluralsight.demo.service.RegistrationFacade;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class RegistrationController {
    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private final RegistrationFacade registrationFacade;

    /**
     * @param registrationFacade
     */
    public RegistrationController(RegistrationFacade registrationFacade) {
        this.registrationFacade = registrationFacade;
    }

    @GetMapping
    public String index(@ModelAttribute("registration") AttendeeRegistration registration) {
        return "index";
    }

    @PostMapping
    public String submit(@ModelAttribute("registration") @Valid AttendeeRegistration registration,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOG.warn("Validation failed: {}", bindingResult);
            return "index";
        }

        registrationFacade.register(OffsetDateTime.now(), registration);

        LOG.debug("Message sent to registration request channel");

        return "success";
    }
}
