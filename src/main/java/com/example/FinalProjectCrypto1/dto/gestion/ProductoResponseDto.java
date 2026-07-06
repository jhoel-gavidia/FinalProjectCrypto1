package com.example.FinalProjectCrypto1.dto.gestion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponseDto {

    private Integer codProducto;
    private String nombreProducto;
    private Integer codCategoria;
    private String nombreCategoria;
    private BigDecimal precioUnitario;
    private BigDecimal precioFraccion;
    private Integer cantidadUnidad;
    private Integer cantidadFraccion;
    private Boolean estado;
}