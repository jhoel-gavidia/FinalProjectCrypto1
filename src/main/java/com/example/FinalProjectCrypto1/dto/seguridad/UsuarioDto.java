package com.example.FinalProjectCrypto1.dto.seguridad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private Integer idUsuario;
    private String usuario;
    private Integer idRol;
    private String nombreRol;
    private Boolean estado;
    private Boolean twoFactorEnabled;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaModificacion;
}