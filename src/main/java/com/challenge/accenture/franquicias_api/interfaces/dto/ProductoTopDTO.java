package com.challenge.accenture.franquicias_api.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Producto con mayor stock por sucursal")
public class ProductoTopDTO {

    @Schema(description = "ID del producto")
    private String productoId;

    @Schema(description = "Nombre del producto")
    private String nombre;

    @Schema(description = "Stock disponible")
    private Integer stock;

    @Schema(description = "ID de la sucursal")
    private String sucursalId;

    @Schema(description = "Nombre de la sucursal")
    private String sucursalNombre;
}