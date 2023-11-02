package com.pluralsight.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

//@ImportResource("classpath:/service-integration-config.xml")
@SpringBootApplication
@EnableIntegration
public class GlobomanticsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlobomanticsServiceApplication.class, args);
    }
}
