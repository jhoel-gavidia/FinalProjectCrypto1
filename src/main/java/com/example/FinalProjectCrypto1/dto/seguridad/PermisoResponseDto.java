package com.example.FinalProjectCrypto1.dto.seguridad;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermisoResponseDto {

    private Integer idRolFuncionalidad;
    private Integer idRol;
    private Integer idFuncionalidad;
    private String nombreFuncionalidad;
    private Boolean ver;
    private Boolean crear;
    private Boolean editar;
    private Boolean eliminar;
    private Boolean imprimir;
}
