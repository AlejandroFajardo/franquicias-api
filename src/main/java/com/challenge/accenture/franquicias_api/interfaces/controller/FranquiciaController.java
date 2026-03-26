package com.challenge.accenture.franquicias_api.interfaces.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.accenture.franquicias_api.domain.model.Franquicia;
import com.challenge.accenture.franquicias_api.domain.model.Producto;
import com.challenge.accenture.franquicias_api.domain.model.Sucursal;
import com.challenge.accenture.franquicias_api.infrastructure.repository.FranquiciaRepository;
import com.challenge.accenture.franquicias_api.interfaces.dto.ProductoTopDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Franquicias", description="Gestion de Franquicias, sucursales y productos")
@RestController
@RequestMapping("/franquicias")
@RequiredArgsConstructor
public class FranquiciaController {

    private final FranquiciaRepository repository;

    @Operation(summary="Crear una nueva franquicia")
    @ApiResponse(responseCode = "200", description = "Franquicia creada correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public Mono<Franquicia> crear(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la franquicia",
                    required = true
            )
        @RequestBody Franquicia franquicia) {
        return repository.save(franquicia);
    }

    @Operation(summary="Agregar una sucursal a una franquicia")
    @ApiResponse(responseCode = "200", description = "Sucursal agregada correctamente")
    @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
    @PostMapping("/{id}/sucursales")
    public Mono<Franquicia> agregarSucursal(
            @Parameter(description = "ID de la franquicia", example = "f1")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos de la sucursal")
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

    @Operation(summary="Agregar un producto a una sucursal")
    @ApiResponse(responseCode = "200", description = "Producto agregado correctamente")
    @ApiResponse(responseCode = "404", description = "Franquicia o sucursal no encontrada")
    @PostMapping("/{franquiciaId}/sucursales/{sucursalId}/productos")
    public Mono<Franquicia> agregarProducto(
            @Parameter(description = "ID de la franquicia", example = "f1")
            @PathVariable String franquiciaId,
            @Parameter(description = "ID de la sucursal", example = "s1")
            @PathVariable String sucursalId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del producto")
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

    @Operation(summary="Eliminar producto de una sucursal")
    @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @DeleteMapping("/productos/{productoId}")
    public Mono<Void> eliminarProducto(
        @Parameter(description="ID del producto", example = "p1")
        @PathVariable String productoId) {
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

    @Operation(summary="Actualizar el stock de un producto")
    @ApiResponse(responseCode="200", description="Stock actualizado")
    @ApiResponse(responseCode="404", description="Producto no encontrado")
    @PutMapping("/productos/{productoId}/stock")
    public Mono<Producto> actualizarStock(
            @Parameter(description = "ID del producto", example = "p1")
            @PathVariable String productoId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevo valor de stock", required = true)
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

    @Operation(summary="Obtener el producto con mayor stock por sucursal")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    @GetMapping("/{franquiciaId}/top-productos")
    public Flux<ProductoTopDTO> obtenerTopProductos(
        @Parameter(description = "ID de la franquicia", example = "f1")
        @PathVariable String franquiciaId) {
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

    @Operation(summary="Actualizar el nombre de la franquicia")
    @ApiResponse(responseCode = "200", description = "Nombre actualizado correctamente")
    @PutMapping("/{franquiciaId}/nombre")
    public Mono<Franquicia> actualizarNombreFranquicia(
        @Parameter(description = "ID de la franquicia", example = "f1")    
        @PathVariable String franquiciaId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevo nombre", required = true)
        @RequestBody String nuevoNombre) {

        return repository.findById(franquiciaId)
                .flatMap(f -> {
                    f.setNombre(nuevoNombre);
                    return repository.save(f);
                });
    }

    @Operation(summary="Actualizar el nombre de una sucursal")
    @ApiResponse(responseCode = "200", description = "Nombre actualizado correctamente")
    @PutMapping("/{franquiciaId}/sucursales/{sucursalId}/nombre")
    public Mono<Franquicia> actualizarNombreSucursal(
            @Parameter(description = "ID de la franquicia", example = "f1")
            @PathVariable String franquiciaId,
            @Parameter(description = "ID de la sucursal", example = "s1")
            @PathVariable String sucursalId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevo nombre", required = true)
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

    @Operation(summary="Actualizar el nombre de un producto")
    @ApiResponse(responseCode = "200", description = "Nombre actualizado correctamente")
    @PutMapping("/productos/{productoId}/nombre")
    public Mono<Producto> actualizarNombreProducto(
            @Parameter(description = "ID del producto", example = "p1")
            @PathVariable String productoId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevo nombre", required = true)
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