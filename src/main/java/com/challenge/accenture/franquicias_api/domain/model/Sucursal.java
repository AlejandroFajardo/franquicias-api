package com.challenge.accenture.franquicias_api.domain.model;

import java.util.List;

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
@Schema(description = "Sucursal perteneciente a una franquicia")
public class Sucursal {

    @Field("_id")
    @Schema(description = "ID de la sucursal", example = "s1")
    private String id;

    @Schema(description = "Nombre de la sucursal", example = "Sucursal norte")
    private String nombre;

    @Schema(description = "ista de productos asociados a la sucursal")
    private List<Producto> productos;
}