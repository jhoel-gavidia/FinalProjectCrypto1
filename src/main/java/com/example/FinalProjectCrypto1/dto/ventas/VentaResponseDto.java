package com.example.FinalProjectCrypto1.dto.ventas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaResponseDto {

    private Integer codVenta;
    private Integer codCliente;
    private String nombreCliente;
    private String tipoDocumento;
    private String numeroDocumento;
    private LocalDateTime fechaHora;
    private Boolean estado;
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
}