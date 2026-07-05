package com.example.FinalProjectCrypto1.dto.ventas;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExtornoRequest {

    @NotNull
    private Integer codVenta;

    private String observacion;

}
