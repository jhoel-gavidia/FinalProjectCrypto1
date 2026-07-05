package com.example.FinalProjectCrypto1.dto.ventas;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class VentaRequest {

    @NotBlank
    private String tipoDocumento;

    @NotNull
    private Integer codCliente;

    @Valid
    private List<VentaDetalleRequest> detalles;

}
