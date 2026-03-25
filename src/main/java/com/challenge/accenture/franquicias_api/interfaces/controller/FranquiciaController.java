package com.challenge.accenture.franquicias_api.interfaces.controller;

import com.challenge.accenture.franquicias_api.domain.model.Producto;
import com.challenge.accenture.franquicias_api.domain.model.Sucursal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import com.challenge.accenture.franquicias_api.domain.model.Franquicia;
import com.challenge.accenture.franquicias_api.infrastructure.repository.FranquiciaRepository;

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

    @PostMapping("/sucursales/{sucursalId}/productos")
    public Mono<Franquicia> agregarProducto(
            @PathVariable String sucursalId,
            @RequestBody Producto producto) {

        return repository.findAll()
                .flatMap(f -> {
                    if (f.getSucursales() != null) {
                        f.getSucursales().forEach(s -> {
                            if (s.getId().equals(sucursalId)) {
                                if (s.getProductos() == null) {
                                    s.setProductos(new java.util.ArrayList<>());
                                }
                                s.getProductos().add(producto);
                            }
                        });
                    }
                    return repository.save(f);
                }).next();
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
}