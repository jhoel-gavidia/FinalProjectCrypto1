package com.example.FinalProjectCrypto1.service.seguridad;

import com.example.FinalProjectCrypto1.dto.seguridad.AsignarPermisosRequest;
import com.example.FinalProjectCrypto1.dto.seguridad.PermisoResponseDto;

import java.util.List;

public interface RolFuncionalidadService {

    List<PermisoResponseDto> listarPorRol(Integer idRol);

    List<PermisoResponseDto> asignarPermisos(Integer idRol, AsignarPermisosRequest request);
}