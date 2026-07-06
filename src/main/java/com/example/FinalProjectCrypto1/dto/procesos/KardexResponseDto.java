package com.example.FinalProjectCrypto1.dto.procesos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KardexResponseDto {

    private Integer codKardex;
    private Integer codProducto;
    private String nombreProducto;
    private String tipoOperacion;
    private Integer cantidadInicial;
    private Integer cantidadMovimiento;
    private Integer cantidadFinal;
    private Integer saldoUnitario;
    private Integer saldoFraccionario;
    private LocalDateTime fechaHora;
    private String codDocumento;
    private String observacion;
    private String usuarioRegistro;
}