package com.example.FinalProjectCrypto1.dto.seguridad;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class AsignarPermisosRequest {

    @Valid
    @NotEmpty(message = "Debe enviar al menos un permiso")
    private List<PermisoItemDto> permisos;
}
