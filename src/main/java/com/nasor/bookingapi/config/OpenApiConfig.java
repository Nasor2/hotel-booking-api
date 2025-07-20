package com.nasor.bookingapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Booking API",
                version = "1.0.0",
                description = "API for managing hotel room bookings, including creation, retrieval, updating, and deletion of reservations.",
                contact = @Contact(
                        name = "Samuel Peña Ortega",
                        email = "penaortegasamuel@gmail.com",
                        url = "https://portfolio-samuel-pos-projects.vercel.app/"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Development Server"),
        }
)
public class OpenApiConfig {}
