package com.app.quantitymeasurement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * QuantityMeasurementApplication
 *
 * Entry point for the Quantity Measurement Spring Boot application.
 *
 * {@code @EnableAsync} activates Spring's asynchronous method execution,
 * allowing {@code @Async} methods in {@code EmailService} to run in a
 * background thread pool so email sending never blocks the HTTP response.
 *
 * @author UC19
 * @version 19.0
 */
@SpringBootApplication
@EnableAsync
@OpenAPIDefinition(
    info = @Info(
        title       = "Quantity Measurement API",
        version     = "19.0",
        description = "REST API for quantity measurement operations — comparison, conversion, " +
                      "addition, subtraction, and division across Length, Weight, Volume, " +
                      "and Temperature units. Includes JWT authentication, Google OAuth2, " +
                      "email notifications, and password management."
    )
)
public class QuantityMeasurementApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuantityMeasurementApplication.class, args);
    }
}
