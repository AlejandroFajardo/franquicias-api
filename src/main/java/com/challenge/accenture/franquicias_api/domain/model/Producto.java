package com.challenge.accenture.franquicias_api.domain.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {
    private String id;
    private String nombre;
    private Integer stock;
}