package com.example.FinalProjectCrypto1.service.seguridad;

import com.example.FinalProjectCrypto1.dto.seguridad.AsignarPermisosRequest;
import com.example.FinalProjectCrypto1.dto.seguridad.PermisoItemDto;
import com.example.FinalProjectCrypto1.dto.seguridad.PermisoResponseDto;
import com.example.FinalProjectCrypto1.exception.ResourceNotFoundException;
import com.example.FinalProjectCrypto1.model.seguridad.Funcionalidad;
import com.example.FinalProjectCrypto1.model.seguridad.Rol;
import com.example.FinalProjectCrypto1.model.seguridad.RolFuncionalidad;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.seguridad.FuncionalidadRepository;
import com.example.FinalProjectCrypto1.repository.seguridad.RolFuncionalidadRepository;
import com.example.FinalProjectCrypto1.repository.seguridad.RolRepository;
import com.example.FinalProjectCrypto1.service.auditoria.AuditoriaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolFuncionalidadServiceImpl implements RolFuncionalidadService {

    private final RolFuncionalidadRepository rolFuncionalidadRepository;
    private final RolRepository rolRepository;
    private final FuncionalidadRepository funcionalidadRepository;
    private final AuditoriaService auditoriaService;
    private final HttpServletRequest httpRequest;

    private Integer usuarioActualId() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return usuario.getIdUsuario();
    }

    private PermisoResponseDto toDto(RolFuncionalidad permiso) {
        return new PermisoResponseDto(
                permiso.getIdRolFuncionalidad(),
                permiso.getIdRol().getIdRol(),
                permiso.getIdFuncionalidad().getIdFuncionalidad(),
                permiso.getIdFuncionalidad().getNombre(),
                permiso.getVer(),
                permiso.getCrear(),
                permiso.getEditar(),
                permiso.getEliminar(),
                permiso.getImprimir()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermisoResponseDto> listarPorRol(Integer idRol) {

        rolRepository.findById(idRol)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));

        return rolFuncionalidadRepository.findByIdRol_IdRol(idRol).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<PermisoResponseDto> asignarPermisos(Integer idRol, AsignarPermisosRequest request) {

        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));

        List<RolFuncionalidad> permisosAnteriores =
                rolFuncionalidadRepository.findByIdRol_IdRol(idRol);

        rolFuncionalidadRepository.deleteByIdRol_IdRol(idRol);

        List<RolFuncionalidad> nuevosPermisos = request.getPermisos().stream()
                .map(item -> construirPermiso(rol, item))
                .collect(Collectors.toList());

        List<RolFuncionalidad> guardados = rolFuncionalidadRepository.saveAll(nuevosPermisos);

        auditoriaService.registrar(
                usuarioActualId(), "Seguridad", "rol_funcionalidad", "UPDATE",
                idRol, permisosAnteriores.size() + " permisos anteriores",
                guardados.size() + " permisos nuevos", httpRequest
        );

        return guardados.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private RolFuncionalidad construirPermiso(Rol rol, PermisoItemDto item) {

        Funcionalidad funcionalidad = funcionalidadRepository
                .findById(item.getIdFuncionalidad())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Funcionalidad no encontrada: id " + item.getIdFuncionalidad()));

        RolFuncionalidad permiso = new RolFuncionalidad();
        permiso.setIdRol(rol);
        permiso.setIdFuncionalidad(funcionalidad);
        permiso.setVer(Boolean.TRUE.equals(item.getVer()));
        permiso.setCrear(Boolean.TRUE.equals(item.getCrear()));
        permiso.setEditar(Boolean.TRUE.equals(item.getEditar()));
        permiso.setEliminar(Boolean.TRUE.equals(item.getEliminar()));
        permiso.setImprimir(Boolean.TRUE.equals(item.getImprimir()));

        return permiso;
    }
}