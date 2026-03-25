package com.challenge.accenture.franquicias_api;

import com.challenge.accenture.franquicias_api.domain.model.Franquicia;
import com.challenge.accenture.franquicias_api.infrastructure.repository.FranquiciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class FranquiciaControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private FranquiciaRepository repository;

    private Franquicia franquicia;

    @BeforeEach
    void setup() {
        repository.deleteAll().block();

        franquicia = Franquicia.builder()
                .nombre("Test")
                .build();

        franquicia = repository.save(franquicia).block();
    }

    @Test
    void crearFranquiciaEndpointTest() {

        webTestClient.post()
                .uri("/franquicias")
                .bodyValue(new Franquicia(null, "Nueva", null))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.nombre").isEqualTo("Nueva");
    }

    @Test
    void topProductosExactoTest() {

        webTestClient.get()
                .uri("/franquicias/" + franquicia.getId() + "/top-productos")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].nombre").exists();
    }

    @Test
    void topProductosValidacionExactaTest() {

        webTestClient.get()
                .uri("/franquicias/" + franquicia.getId() + "/top-productos")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2);
    }
}