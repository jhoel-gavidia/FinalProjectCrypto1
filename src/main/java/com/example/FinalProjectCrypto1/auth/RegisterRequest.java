package com.example.FinalProjectCrypto1.auth;

import lombok.Data;

@Data
public class RegisterRequest {

    private String usuario;

    private String password;

    private Integer idRol;
}
