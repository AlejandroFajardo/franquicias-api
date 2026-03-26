package com.challenge.accenture.franquicias_api.domain.model;

import org.springframework.data.mongodb.core.mapping.Field;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Producto disponible en una sucursal")
public class Producto {

    @Field("_id")
    @Schema(description = "ID del producto", example = "p1")
    private String id;

    @Schema(description = "Nombre del producto", example = "Hamburguesa")
    private String nombre;

    @Schema(description = "cantidad en stock", example = "50")
    private Integer stock;
}