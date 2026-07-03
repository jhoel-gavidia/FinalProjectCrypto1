package com.example.FinalProjectCrypto1.dto.seguridad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 4, max = 30)
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 20)
    private String password;

    @NotNull(message = "Debe seleccionar un rol")
    private Integer idRol;
}
