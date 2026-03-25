package com.challenge.accenture.franquicias_api.interfaces.controller;

import com.challenge.accenture.franquicias_api.domain.model.Producto;
import com.challenge.accenture.franquicias_api.domain.model.Sucursal;
import com.challenge.accenture.franquicias_api.interfaces.dto.ProductoTopDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.challenge.accenture.franquicias_api.domain.model.Franquicia;
import com.challenge.accenture.franquicias_api.infrastructure.repository.FranquiciaRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/franquicias")
@RequiredArgsConstructor
public class FranquiciaController {

    private final FranquiciaRepository repository;

    @PostMapping
    public Mono<Franquicia> crear(@RequestBody Franquicia franquicia) {
        return repository.save(franquicia);
    }

    @PostMapping("/{id}/sucursales")
    public Mono<Franquicia> agregarSucursal(
            @PathVariable String id,
            @RequestBody Sucursal sucursal) {

        return repository.findById(id)
                .flatMap(f -> {
                    if (f.getSucursales() == null) {
                        f.setSucursales(new java.util.ArrayList<>());
                    }
                    f.getSucursales().add(sucursal);
                    return repository.save(f);
                });
    }

    @PostMapping("/{franquiciaId}/sucursales/{sucursalId}/productos")
    public Mono<Franquicia> agregarProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @RequestBody Producto producto) {

        return repository.findById(franquiciaId)
                .flatMap(f -> {
                    if (f.getSucursales() != null) {
                        f.getSucursales().forEach(s -> {
                            if (s.getId().equals(sucursalId)) {
                                if (s.getProductos() == null) {
                                    s.setProductos(new ArrayList<>());
                                }
                                s.getProductos().add(producto);
                            }
                        });
                    }
                    return repository.save(f);
                });
    }

    @DeleteMapping("/productos/{productoId}")
    public Mono<Void> eliminarProducto(@PathVariable String productoId) {

        return repository.findAll()
                .flatMap(f -> {
                    if (f.getSucursales() != null) {
                        f.getSucursales().forEach(s -> {
                            if (s.getProductos() != null) {
                                s.getProductos().removeIf(p -> p.getId().equals(productoId));
                            }
                        });
                    }
                    return repository.save(f);
                })
                .then();
    }

    @PutMapping("/productos/{productoId}/stock")
    public Mono<Producto> actualizarStock(
            @PathVariable String productoId,
            @RequestBody Integer nuevoStock) {

        return repository.findAll()
                .flatMap(f -> {
                    if (f.getSucursales() != null) {
                        f.getSucursales().forEach(s -> {
                            if (s.getProductos() != null) {
                                s.getProductos().forEach(p -> {
                                    if (p.getId().equals(productoId)) {
                                        p.setStock(nuevoStock);
                                    }
                                });
                            }
                        });
                    }
                    return repository.save(f);
                })
                .flatMap(f -> reactor.core.publisher.Flux.fromIterable(f.getSucursales()))
                .flatMap(s -> reactor.core.publisher.Flux.fromIterable(s.getProductos()))
                .filter(p -> p.getId().equals(productoId))
                .next();
    }

    @GetMapping("/{franquiciaId}/top-productos")
    public Flux<ProductoTopDTO> obtenerTopProductos(@PathVariable String franquiciaId) {

        return repository.findById(franquiciaId)
                .flatMapMany(f ->
                        Flux.fromIterable(f.getSucursales() == null ? List.of() : f.getSucursales())
                )
                .flatMap(sucursal ->
                        Flux.fromIterable(sucursal.getProductos() == null ? List.of() : sucursal.getProductos())
                                .sort((p1, p2) -> p2.getStock().compareTo(p1.getStock()))
                                .take(1)
                                .map(p -> new ProductoTopDTO(
                                        p.getId(),
                                        p.getNombre(),
                                        p.getStock(),
                                        sucursal.getId(),
                                        sucursal.getNombre()
                                ))
                );
    }

    @PutMapping("/{franquiciaId}/nombre")
    public Mono<Franquicia> actualizarNombreFranquicia(
            @PathVariable String franquiciaId,
            @RequestBody String nuevoNombre) {

        return repository.findById(franquiciaId)
                .flatMap(f -> {
                    f.setNombre(nuevoNombre);
                    return repository.save(f);
                });
    }

    @PutMapping("/{franquiciaId}/sucursales/{sucursalId}/nombre")
    public Mono<Franquicia> actualizarNombreSucursal(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @RequestBody String nuevoNombre) {

        return repository.findById(franquiciaId)
                .flatMap(f -> {
                    if (f.getSucursales() != null) {
                        f.getSucursales().forEach(s -> {
                            if (s.getId().equals(sucursalId)) {
                                s.setNombre(nuevoNombre);
                            }
                        });
                    }
                    return repository.save(f);
                });
    }

    @PutMapping("/productos/{productoId}/nombre")
    public Mono<Producto> actualizarNombreProducto(
            @PathVariable String productoId,
            @RequestBody String nuevoNombre) {

        return repository.findAll()
                .flatMap(f -> {
                    if (f.getSucursales() != null) {
                        f.getSucursales().forEach(s -> {
                            if (s.getProductos() != null) {
                                s.getProductos().forEach(p -> {
                                    if (p.getId().equals(productoId)) {
                                        p.setNombre(nuevoNombre);
                                    }
                                });
                            }
                        });
                    }
                    return repository.save(f);
                })
                .flatMap(f -> Flux.fromIterable(f.getSucursales()))
                .flatMap(s -> Flux.fromIterable(s.getProductos()))
                .filter(p -> p.getId().equals(productoId))
                .next();
    }
}