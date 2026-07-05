package com.example.FinalProjectCrypto1.service.seguridad;

import com.example.FinalProjectCrypto1.exception.DuplicateResourceException;
import com.example.FinalProjectCrypto1.exception.ResourceNotFoundException;
import com.example.FinalProjectCrypto1.model.seguridad.Rol;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.seguridad.RolRepository;
import com.example.FinalProjectCrypto1.service.auditoria.AuditoriaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private static final String ROL_PROTEGIDO = "SUPERUSUARIO";

    private final RolRepository rolRepository;
    private final AuditoriaService auditoriaService;
    private final HttpServletRequest httpRequest;

    private Integer usuarioActualId() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return usuario.getIdUsuario();
    }

    @Override
    public List<Rol> listar() {
        return rolRepository.findByEstadoTrueOrderByNombreRolAsc();
    }

    @Override
    public Rol buscarPorId(Integer id) {
        return rolRepository.findByIdRolAndEstadoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));
    }

    @Override
    public Rol guardar(Rol rol) {

        rol.setNombreRol(rol.getNombreRol().trim());

        if (rolRepository.findByNombreRolIgnoreCase(rol.getNombreRol()).isPresent()) {
            throw new DuplicateResourceException("El rol ya existe");
        }

        rol.setEstado(true);

        Rol rolGuardado = rolRepository.save(rol);

        auditoriaService.registrar(
                usuarioActualId(), "Seguridad", "rol", "INSERT",
                rolGuardado.getIdRol(), null, rolGuardado, httpRequest
        );

        return rolGuardado;
    }

    @Override
    public Rol actualizar(Integer id, Rol rol) {

        Rol rolBD = buscarPorId(id);

        if (rolBD.getNombreRol().equalsIgnoreCase(ROL_PROTEGIDO)) {
            throw new RuntimeException("El rol Superusuario no puede modificarse");
        }

        Rol snapshotAnterior = new Rol(rolBD.getIdRol(), rolBD.getNombreRol(), rolBD.getEstado());

        rol.setNombreRol(rol.getNombreRol().trim());

        Optional<Rol> existente = rolRepository.findByNombreRolIgnoreCase(rol.getNombreRol());
        if (existente.isPresent() && !existente.get().getIdRol().equals(id)) {
            throw new DuplicateResourceException("El rol ya existe");
        }

        rolBD.setNombreRol(rol.getNombreRol());

        Rol rolActualizado = rolRepository.save(rolBD);

        auditoriaService.registrar(
                usuarioActualId(), "Seguridad", "rol", "UPDATE",
                rolActualizado.getIdRol(), snapshotAnterior, rolActualizado, httpRequest
        );

        return rolActualizado;
    }

    @Override
    public Rol eliminar(Integer id) {

        Rol rol = buscarPorId(id);

        if (rol.getNombreRol().equalsIgnoreCase(ROL_PROTEGIDO)) {
            throw new RuntimeException("El rol Superusuario no puede eliminarse");
        }

        Rol snapshotAnterior = new Rol(rol.getIdRol(), rol.getNombreRol(), rol.getEstado());

        rol.setEstado(false);

        Rol rolEliminado = rolRepository.save(rol);

        auditoriaService.registrar(
                usuarioActualId(), "Seguridad", "rol", "DELETE",
                rolEliminado.getIdRol(), snapshotAnterior, rolEliminado, httpRequest
        );

        return rolEliminado;
    }
}