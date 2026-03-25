package com.challenge.accenture.franquicias_api.domain.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sucursal {
    @Field("_id")
    private String id;
    private String nombre;
    private List<Producto> productos;
}