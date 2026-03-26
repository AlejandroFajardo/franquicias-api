package com.challenge.accenture.franquicias_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Franquicias API")
                        .version("1.0")
                        .description("API reactiva para gestión de franquicias")
                        .contact(new Contact()
                            .name("Alejandro Fajardo")
                            .email("alejandrofajardoannear@gmail.com")
                ));
    }
}