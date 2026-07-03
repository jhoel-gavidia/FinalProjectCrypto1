package com.example.FinalProjectCrypto1.dto.seguridad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CambiarPasswordDto {
    @NotBlank
    private String passwordActual;

    @NotBlank
    @Size(min = 6, max = 20)
    private String passwordNueva;
}
