package com.example.FinalProjectCrypto1.dto.procesos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class IngresoProductoDTO {

    @NotNull(message = "El producto es obligatorio")
    private Integer codProducto;

    @NotNull(message = "La cantidad de unidades es obligatoria")
    @Min(value = 0, message = "No puede ser negativa")
    private Integer cantidadUnidad;

    @NotNull(message = "La cantidad de fracciones es obligatoria")
    @Min(value = 0, message = "No puede ser negativa")
    private Integer cantidadFraccion;

    @Size(max = 200)
    private String observacion;

}
