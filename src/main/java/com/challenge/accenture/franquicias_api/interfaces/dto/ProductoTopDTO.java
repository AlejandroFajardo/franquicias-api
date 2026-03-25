package com.challenge.accenture.franquicias_api.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoTopDTO {

    private String productoId;
    private String nombre;
    private Integer stock;

    private String sucursalId;
    private String sucursalNombre;
}