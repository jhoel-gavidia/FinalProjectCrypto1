package com.example.FinalProjectCrypto1.service.seguridad;

import com.example.FinalProjectCrypto1.dto.seguridad.FuncionalidadNodoDto;
import com.example.FinalProjectCrypto1.exception.DuplicateResourceException;
import com.example.FinalProjectCrypto1.exception.ResourceNotFoundException;
import com.example.FinalProjectCrypto1.model.seguridad.Funcionalidad;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.seguridad.FuncionalidadRepository;
import com.example.FinalProjectCrypto1.service.auditoria.AuditoriaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionalidadServiceImpl implements FuncionalidadService {

    private final FuncionalidadRepository funcionalidadRepository;
    private final AuditoriaService auditoriaService;
    private final HttpServletRequest httpRequest;

    private Integer usuarioActualId() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return usuario.getIdUsuario();
    }

    @Override
    public List<Funcionalidad> listar() {
        return funcionalidadRepository.findAllByOrderByNombreAsc();
    }

    @Override
    public List<FuncionalidadNodoDto> obtenerArbol() {

        List<Funcionalidad> raices =
                funcionalidadRepository.findByPadreIsNullOrderByNombreAsc();

        return raices.stream()
                .map(this::construirNodo)
                .collect(Collectors.toList());
    }

    private FuncionalidadNodoDto construirNodo(Funcionalidad funcionalidad) {

        List<Funcionalidad> hijos = funcionalidadRepository
                .findByPadre_IdFuncionalidadOrderByNombreAsc(funcionalidad.getIdFuncionalidad());

        List<FuncionalidadNodoDto> hijosDto = hijos.stream()
                .map(this::construirNodo)
                .collect(Collectors.toList());

        return new FuncionalidadNodoDto(
                funcionalidad.getIdFuncionalidad(),
                funcionalidad.getNombre(),
                funcionalidad.getIcono(),
                hijosDto
        );
    }

    @Override
    public Funcionalidad buscarPorId(Integer id) {
        return funcionalidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionalidad no encontrada"));
    }

    @Override
    public Funcionalidad guardar(Funcionalidad funcionalidad) {

        funcionalidad.setNombre(funcionalidad.getNombre().trim());

        if (funcionalidadRepository.findByNombreIgnoreCase(funcionalidad.getNombre()).isPresent()) {
            throw new DuplicateResourceException("La funcionalidad ya existe");
        }

        if (funcionalidad.getPadre() != null) {
            Funcionalidad padre = buscarPorId(funcionalidad.getPadre().getIdFuncionalidad());
            funcionalidad.setPadre(padre);
        }

        Funcionalidad guardada = funcionalidadRepository.save(funcionalidad);

        auditoriaService.registrar(
                usuarioActualId(), "Seguridad", "funcionalidad", "INSERT",
                guardada.getIdFuncionalidad(), null, guardada, httpRequest
        );

        return guardada;
    }

    @Override
    public Funcionalidad actualizar(Integer id, Funcionalidad funcionalidad) {

        Funcionalidad funcionalidadBD = buscarPorId(id);

        Funcionalidad snapshotAnterior = new Funcionalidad(
                funcionalidadBD.getIdFuncionalidad(),
                funcionalidadBD.getNombre(),
                funcionalidadBD.getIcono(),
                funcionalidadBD.getPadre(),
                null
        );

        funcionalidad.setNombre(funcionalidad.getNombre().trim());

        Optional<Funcionalidad> existente =
                funcionalidadRepository.findByNombreIgnoreCase(funcionalidad.getNombre());

        if (existente.isPresent() && !existente.get().getIdFuncionalidad().equals(id)) {
            throw new DuplicateResourceException("La funcionalidad ya existe");
        }

        if (funcionalidad.getPadre() != null) {

            Integer idPadre = funcionalidad.getPadre().getIdFuncionalidad();

            if (idPadre.equals(id)) {
                throw new RuntimeException("Una funcionalidad no puede ser padre de sí misma");
            }

            funcionalidadBD.setPadre(buscarPorId(idPadre));

        } else {
            funcionalidadBD.setPadre(null);
        }

        funcionalidadBD.setNombre(funcionalidad.getNombre());
        funcionalidadBD.setIcono(funcionalidad.getIcono());

        Funcionalidad actualizada = funcionalidadRepository.save(funcionalidadBD);

        auditoriaService.registrar(
                usuarioActualId(), "Seguridad", "funcionalidad", "UPDATE",
                actualizada.getIdFuncionalidad(), snapshotAnterior, actualizada, httpRequest
        );

        return actualizada;
    }

    @Override
    public void eliminar(Integer id) {

        Funcionalidad funcionalidad = buscarPorId(id);

        List<Funcionalidad> hijos = funcionalidadRepository
                .findByPadre_IdFuncionalidadOrderByNombreAsc(id);

        if (!hijos.isEmpty()) {
            throw new RuntimeException(
                    "No se puede eliminar: la funcionalidad tiene hijos");
        }

        funcionalidadRepository.delete(funcionalidad);

        auditoriaService.registrar(
                usuarioActualId(), "Seguridad", "funcionalidad", "DELETE",
                id, funcionalidad, null, httpRequest
        );
    }
}