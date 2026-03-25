package com.challenge.accenture.franquicias_api;

import com.challenge.accenture.franquicias_api.domain.model.Franquicia;
import com.challenge.accenture.franquicias_api.domain.model.Producto;
import com.challenge.accenture.franquicias_api.domain.model.Sucursal;
import com.challenge.accenture.franquicias_api.infrastructure.repository.FranquiciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

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

        repository.deleteAll().block();

        Producto p1 = Producto.builder().id("p1").nombre("Hamburguesa").stock(50).build();
        Producto p2 = Producto.builder().id("p2").nombre("Papas").stock(99).build();
        Producto p3 = Producto.builder().id("p3").nombre("Gaseosa").stock(70).build();

        Producto p4 = Producto.builder().id("p4").nombre("Helado").stock(15).build();
        Producto p5 = Producto.builder().id("p5").nombre("Cafe").stock(40).build();

        Sucursal s1 = Sucursal.builder()
                .id("s1")
                .nombre("Centro")
                .productos(new ArrayList<>(List.of(p1, p2, p3)))
                .build();

        Sucursal s2 = Sucursal.builder()
                .id("s2")
                .nombre("Norte")
                .productos(new ArrayList<>(List.of(p4, p5)))
                .build();

        Franquicia franquicia = Franquicia.builder()
                .nombre("Test")
                .sucursales(new ArrayList<>(List.of(s1, s2)))
                .build();

        franquicia = repository.save(franquicia).block();
        repository.findAll().collectList().block();

        webTestClient.get()
                .uri("/franquicias/" + franquicia.getId() + "/top-productos")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].nombre").isEqualTo("Papas")
                .jsonPath("$[1].nombre").isEqualTo("Cafe");
    }
    
}
