package com.challenge.accenture.franquicias_api;

import com.challenge.accenture.franquicias_api.domain.model.*;
import com.challenge.accenture.franquicias_api.infrastructure.repository.FranquiciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FranquiciaServiceTest {

    @Autowired
    private FranquiciaRepository repository;

    private Franquicia franquicia;

    @BeforeEach
    void setup() {
        repository.deleteAll().block();

        Producto p1 = Producto.builder().id("p1").nombre("Hamburguesa").stock(50).build();
        Producto p2 = Producto.builder().id("p2").nombre("Papas").stock(20).build();
        Producto p3 = Producto.builder().id("p3").nombre("Gaseosa").stock(70).build();

        Sucursal s1 = Sucursal.builder()
                .id("s1")
                .nombre("Centro")
                .productos(new ArrayList<>(List.of(p1, p2, p3)))
                .build();

        Producto p4 = Producto.builder().id("p4").nombre("Helado").stock(15).build();
        Producto p5 = Producto.builder().id("p5").nombre("Cafe").stock(40).build();

        Sucursal s2 = Sucursal.builder()
                .id("s2")
                .nombre("Norte")
                .productos(new ArrayList<>(List.of(p4, p5)))
                .build();

        franquicia = Franquicia.builder()
                .nombre("Test")
                .sucursales(new ArrayList<>(List.of(s1, s2)))
                .build();

        franquicia = repository.save(franquicia).block();
    }

    // ===============================
    // TEST CREAR
    // ===============================
    @Test
    void crearFranquiciaTest() {
        Franquicia nueva = Franquicia.builder()
                .nombre("Nueva")
                .build();

        StepVerifier.create(repository.save(nueva))
                .expectNextMatches(f -> f.getNombre().equals("Nueva"))
                .verifyComplete();
    }

    // ===============================
    // TEST ELIMINAR PRODUCTO
    // ===============================
    @Test
    void eliminarProductoTest() {

        StepVerifier.create(
                        repository.findById(franquicia.getId())
                                .flatMap(f -> {
                                    f.getSucursales().forEach(s ->
                                            s.getProductos().removeIf(p -> p.getId().equals("p1"))
                                    );
                                    return repository.save(f);
                                })
                )
                .expectNextMatches(f ->
                        f.getSucursales().stream()
                                .flatMap(s -> s.getProductos().stream())
                                .noneMatch(p -> p.getId().equals("p1"))
                )
                .verifyComplete();
    }

    // ===============================
    // TEST ACTUALIZAR STOCK
    // ===============================
    @Test
    void actualizarStockTest() {

        StepVerifier.create(
                        repository.findById(franquicia.getId())
                                .flatMap(f -> {
                                    f.getSucursales().forEach(s ->
                                            s.getProductos().forEach(p -> {
                                                if (p.getId().equals("p2")) {
                                                    p.setStock(99);
                                                }
                                            })
                                    );
                                    return repository.save(f);
                                })
                                .flatMapMany(f -> Flux.fromIterable(f.getSucursales()))
                                .flatMap(s -> Flux.fromIterable(s.getProductos()))
                                .filter(p -> p.getId().equals("p2"))
                                .next()
                )
                .expectNextMatches(p -> p.getStock() == 99)
                .verifyComplete();
    }

    // ===============================
    // TEST TOP PRODUCTOS
    // ===============================
    @Test
    void topProductosTest() {

        StepVerifier.create(
                        repository.findById(franquicia.getId())
                                .flatMapMany(f -> Flux.fromIterable(f.getSucursales()))
                                .flatMap(s ->
                                        Flux.fromIterable(s.getProductos())
                                                .sort((a, b) -> b.getStock() - a.getStock())
                                                .next()
                                )
                )
                .expectNextCount(2) // 2 sucursales
                .verifyComplete();
    }
}