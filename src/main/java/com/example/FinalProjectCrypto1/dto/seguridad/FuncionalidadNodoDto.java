package com.example.FinalProjectCrypto1.dto.seguridad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionalidadNodoDto {

    private Integer idFuncionalidad;
    private String nombre;
    private String icono;
    private List<FuncionalidadNodoDto> hijos;
}