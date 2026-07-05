package com.example.FinalProjectCrypto1.service.seguridad;

import com.example.FinalProjectCrypto1.dto.seguridad.AsignarPermisosRequest;
import com.example.FinalProjectCrypto1.model.seguridad.RolFuncionalidad;

import java.util.List;

public interface RolFuncionalidadService {

    List<RolFuncionalidad> listarPorRol(Integer idRol);

    List<RolFuncionalidad> asignarPermisos(Integer idRol, AsignarPermisosRequest request);
}