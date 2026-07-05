package com.example.FinalProjectCrypto1.dto.seguridad;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CambiarRolRequest {

    @NotNull(message = "Debe seleccionar un rol")
    private Integer idRol;
}