package com.example.FinalProjectCrypto1.dto.seguridad;

import lombok.Data;

@Data
public class CambiarPasswordDto {
    private String passwordActual;
    private String passwordNueva;
}
