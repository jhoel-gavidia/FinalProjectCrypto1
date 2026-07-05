package com.example.FinalProjectCrypto1.dto.seguridad;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermisoItemDto {

    @NotNull(message = "El idFuncionalidad es obligatorio")
    private Integer idFuncionalidad;

    private Boolean ver;
    private Boolean crear;
    private Boolean editar;
    private Boolean eliminar;
    private Boolean imprimir;
}