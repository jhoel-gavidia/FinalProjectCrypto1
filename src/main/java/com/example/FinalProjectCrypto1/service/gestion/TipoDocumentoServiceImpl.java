package com.example.FinalProjectCrypto1.service.gestion;

import com.example.FinalProjectCrypto1.exception.DuplicateResourceException;
import com.example.FinalProjectCrypto1.exception.ResourceNotFoundException;
import com.example.FinalProjectCrypto1.model.gestion.TipoDocumento;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.gestion.TipoDocumentoRepository;
import com.example.FinalProjectCrypto1.service.auditoria.AuditoriaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final AuditoriaService auditoriaService;
    private final HttpServletRequest httpRequest;

    private Integer usuarioActualId() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return usuario.getIdUsuario();
    }

    private String resumen(TipoDocumento t) {
        return "descripcion=" + t.getDescripcion() + ", estado=" + t.getEstado();
    }

    @Override
    public List<TipoDocumento> listar() {
        return tipoDocumentoRepository.findByEstadoTrueOrderByDescripcionAsc();
    }

    @Override
    public TipoDocumento buscarPorId(Integer id) {
        return tipoDocumentoRepository.findByCodTipoDocumentoAndEstadoTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tipo de documento no encontrado"));
    }

    @Override
    public TipoDocumento guardar(TipoDocumento tipoDocumento) {

        tipoDocumento.setDescripcion(tipoDocumento.getDescripcion().trim());

        if (tipoDocumentoRepository.findByDescripcionIgnoreCase(tipoDocumento.getDescripcion()).isPresent()) {
            throw new DuplicateResourceException("El tipo de documento ya existe");
        }

        tipoDocumento.setEstado(true);

        TipoDocumento tipoDocumentoGuardado = tipoDocumentoRepository.save(tipoDocumento);

        auditoriaService.registrar(
                usuarioActualId(),
                "Gestion",
                "tipo_documento",
                "INSERT",
                tipoDocumentoGuardado.getCodTipoDocumento(),
                null,
                resumen(tipoDocumentoGuardado),
                httpRequest
        );

        return tipoDocumentoGuardado;
    }

    @Override
    public TipoDocumento actualizar(Integer id, TipoDocumento tipoDocumento) {

        TipoDocumento tipoDocumentoBD = buscarPorId(id);

        String snapshotAnterior = resumen(tipoDocumentoBD);

        tipoDocumento.setDescripcion(tipoDocumento.getDescripcion().trim());

        Optional<TipoDocumento> existente =
                tipoDocumentoRepository.findByDescripcionIgnoreCase(tipoDocumento.getDescripcion());

        if (existente.isPresent()
                && !existente.get().getCodTipoDocumento().equals(id)) {
            throw new DuplicateResourceException("El tipo de documento ya existe");
        }

        tipoDocumentoBD.setDescripcion(tipoDocumento.getDescripcion());

        TipoDocumento tipoDocumentoActualizado = tipoDocumentoRepository.save(tipoDocumentoBD);

        auditoriaService.registrar(
                usuarioActualId(),
                "Gestion",
                "tipo_documento",
                "UPDATE",
                tipoDocumentoActualizado.getCodTipoDocumento(),
                snapshotAnterior,
                resumen(tipoDocumentoActualizado),
                httpRequest
        );

        return tipoDocumentoActualizado;
    }

    @Override
    public TipoDocumento eliminar(Integer id) {

        TipoDocumento tipoDocumento = buscarPorId(id);

        tipoDocumento.setEstado(false);

        TipoDocumento tipoDocumentoEliminado = tipoDocumentoRepository.save(tipoDocumento);

        auditoriaService.registrar(
                usuarioActualId(),
                "Gestion",
                "tipo_documento",
                "DELETE",
                tipoDocumentoEliminado.getCodTipoDocumento(),
                "estado: true",
                "estado: false",
                httpRequest
        );

        return tipoDocumentoEliminado;
    }

}