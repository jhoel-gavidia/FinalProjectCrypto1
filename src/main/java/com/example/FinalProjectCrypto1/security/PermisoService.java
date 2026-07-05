package com.example.FinalProjectCrypto1.security;

import com.example.FinalProjectCrypto1.model.seguridad.RolFuncionalidad;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.seguridad.RolFuncionalidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermisoService {

    private static final String ROL_SUPERUSUARIO = "SUPERUSUARIO";

    private final RolFuncionalidadRepository rolFuncionalidadRepository;

    public boolean tienePermiso(String nombreFuncionalidad, String accion) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {
            return false;
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        if (usuario.getRol().getNombreRol().equalsIgnoreCase(ROL_SUPERUSUARIO)) {
            return true;
        }

        Optional<RolFuncionalidad> permiso = rolFuncionalidadRepository
                .findByIdRol_IdRolAndIdFuncionalidad_NombreIgnoreCase(
                        usuario.getRol().getIdRol(), nombreFuncionalidad);

        if (permiso.isEmpty()) {
            return false;
        }

        RolFuncionalidad p = permiso.get();

        return switch (accion.toUpperCase()) {
            case "VER" -> Boolean.TRUE.equals(p.getVer());
            case "CREAR" -> Boolean.TRUE.equals(p.getCrear());
            case "EDITAR" -> Boolean.TRUE.equals(p.getEditar());
            case "ELIMINAR" -> Boolean.TRUE.equals(p.getEliminar());
            case "IMPRIMIR" -> Boolean.TRUE.equals(p.getImprimir());
            default -> false;
        };
    }
}