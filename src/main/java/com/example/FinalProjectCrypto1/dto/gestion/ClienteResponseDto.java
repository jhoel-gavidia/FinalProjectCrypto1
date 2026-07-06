package com.example.FinalProjectCrypto1.dto.gestion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDto {

    private Integer codCliente;
    private Integer codTipoDocumento;
    private String descripcionTipoDocumento;
    private String numeroDocumento;
    private String razonSocial;
    private String nombreCliente;
    private String fechaNacimiento;
    private Boolean estado;
}