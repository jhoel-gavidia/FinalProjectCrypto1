package com.example.FinalProjectCrypto1.dto.ventas;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VentaDetalleRequest {

    @NotNull
    private Integer codProducto;

    @Min(0)
    private Integer cantidadUnidad;

    @Min(0)
    private Integer cantidadFraccion;

}
