package com.example.FinalProjectCrypto1.service.seguridad;

import com.example.FinalProjectCrypto1.dto.seguridad.UsuarioDto;
import com.example.FinalProjectCrypto1.exception.ResourceNotFoundException;
import com.example.FinalProjectCrypto1.model.seguridad.Rol;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.seguridad.RolRepository;
import com.example.FinalProjectCrypto1.repository.seguridad.UsuarioRespository;
import com.example.FinalProjectCrypto1.service.auditoria.AuditoriaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private static final String ROL_PROTEGIDO = "SUPERUSUARIO";

    private final UsuarioRespository usuarioRepository;
    private final RolRepository rolRepository;
    private final AuditoriaService auditoriaService;
    private final HttpServletRequest httpRequest;

    private Integer usuarioActualId() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return usuario.getIdUsuario();
    }

    private UsuarioDto toDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getIdUsuario(),
                usuario.getUsuario(),
                usuario.getRol().getIdRol(),
                usuario.getRol().getNombreRol(),
                usuario.getEstado(),
                usuario.getTwoFactorEnabled(),
                usuario.getFechaRegistro(),
                usuario.getFechaModificacion()
        );
    }

    private Usuario buscarEntidadPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    private boolean esUltimoSuperusuarioActivo(Usuario usuario) {

        boolean esSuperusuario = usuario.getRol().getNombreRol().equalsIgnoreCase(ROL_PROTEGIDO);

        if (!esSuperusuario || !Boolean.TRUE.equals(usuario.getEstado())) {
            return false;
        }

        long activos = usuarioRepository
                .countByRol_NombreRolIgnoreCaseAndEstadoTrue(ROL_PROTEGIDO);

        return activos <= 1;
    }

    @Override
    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto buscarPorId(Integer id) {
        return toDto(buscarEntidadPorId(id));
    }

    @Override
    public UsuarioDto cambiarRol(Integer id, Integer idRol) {

        Usuario usuario = buscarEntidadPorId(id);

        if (esUltimoSuperusuarioActivo(usuario)
                && !usuario.getRol().getIdRol().equals(idRol)) {

            throw new RuntimeException(
                    "No se puede quitar el rol Superusuario al último usuario que lo tiene");
        }

        Rol nuevoRol = rolRepository.findById(idRol)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));

        String rolAnterior = usuario.getRol().getNombreRol();

        usuario.setRol(nuevoRol);

        Usuario actualizado = usuarioRepository.save(usuario);

        auditoriaService.registrar(
                usuarioActualId(), "Seguridad", "usuario", "UPDATE",
                actualizado.getIdUsuario(),
                "rol=" + rolAnterior,
                "rol=" + nuevoRol.getNombreRol(),
                httpRequest
        );

        return toDto(actualizado);
    }

    @Override
    public UsuarioDto cambiarEstado(Integer id, Boolean estado) {

        Usuario usuario = buscarEntidadPorId(id);

        if (Boolean.FALSE.equals(estado) && esUltimoSuperusuarioActivo(usuario)) {
            throw new RuntimeException(
                    "No se puede desactivar al último Superusuario del sistema");
        }

        Boolean estadoAnterior = usuario.getEstado();

        usuario.setEstado(estado);

        Usuario actualizado = usuarioRepository.save(usuario);

        auditoriaService.registrar(
                usuarioActualId(), "Seguridad", "usuario", "UPDATE",
                actualizado.getIdUsuario(),
                "estado=" + estadoAnterior,
                "estado=" + actualizado.getEstado(),
                httpRequest
        );

        return toDto(actualizado);
    }
}