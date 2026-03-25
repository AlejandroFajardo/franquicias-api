package com.challenge.accenture.franquicias_api.domain.model;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sucursal {
    private String id;
    private String nombre;
    private List<Producto> productos;
}