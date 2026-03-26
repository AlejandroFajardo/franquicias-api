package com.challenge.accenture.franquicias_api.domain.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "franquicias")
@Schema(description = "Entidad que representa una franquicia")
public class Franquicia {

    @Id
    @Schema(description = "ID de la franquicia", example = "f1")
    private String id;

    @Schema(description = "Nombre de la franquicia", example = "Franquicia demo")
    private String nombre;

    @Schema(description = "ista de sucursales asociadas a la franquicia")
    private List<Sucursal> sucursales;
}