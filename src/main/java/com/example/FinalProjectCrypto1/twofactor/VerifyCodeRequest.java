package com.example.FinalProjectCrypto1.twofactor;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerifyCodeRequest {

    @NotNull(message = "El código es obligatorio")
    private Integer codigo;

}
