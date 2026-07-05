package com.example.FinalProjectCrypto1.twofactor;

import lombok.Data;

@Data
public class VerifyCodeRequest {

    private Integer idUsuario;

    private Integer codigo;

}
