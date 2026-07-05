package com.example.FinalProjectCrypto1.dto.seguridad;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CambiarEstadoRequest {

    @NotNull(message = "Debe indicar el estado")
    private Boolean estado;
}