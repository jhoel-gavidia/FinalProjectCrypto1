package com.example.FinalProjectCrypto1.dto.seguridad;

import lombok.Data;

@Data
public class RegisterRequest {

    private String usuario;

    private String password;

    private Integer idRol;
}
