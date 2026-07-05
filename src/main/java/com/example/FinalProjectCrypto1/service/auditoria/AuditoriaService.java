package com.example.FinalProjectCrypto1.service.auditoria;

import jakarta.servlet.http.HttpServletRequest;

public interface AuditoriaService {

    void registrar(
            Integer codUsuario,
            String modulo,
            String tabla,
            String operacion,
            Integer codigoRegistro,
            Object valorAnterior,
            Object valorNuevo,
            HttpServletRequest request
    );

}
