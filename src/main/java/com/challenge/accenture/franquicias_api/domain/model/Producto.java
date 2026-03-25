package com.challenge.accenture.franquicias_api.domain.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {
    @Field("_id")
    private String id;
    private String nombre;
    private Integer stock;
}