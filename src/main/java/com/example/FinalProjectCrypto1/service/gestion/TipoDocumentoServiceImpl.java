package com.example.FinalProjectCrypto1.service.gestion;

import com.example.FinalProjectCrypto1.exception.DuplicateResourceException;
import com.example.FinalProjectCrypto1.exception.ResourceNotFoundException;
import com.example.FinalProjectCrypto1.model.gestion.TipoDocumento;
import com.example.FinalProjectCrypto1.repository.gestion.TipoDocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    private final TipoDocumentoRepository tipoDocumentoRepository;

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

        return tipoDocumentoRepository.save(tipoDocumento);
    }

    @Override
    public TipoDocumento actualizar(Integer id, TipoDocumento tipoDocumento) {

        TipoDocumento tipoDocumentoBD = buscarPorId(id);

        tipoDocumento.setDescripcion(tipoDocumento.getDescripcion().trim());

        Optional<TipoDocumento> existente =
                tipoDocumentoRepository.findByDescripcionIgnoreCase(tipoDocumento.getDescripcion());

        if (existente.isPresent()
                && !existente.get().getCodTipoDocumento().equals(id)) {
            throw new DuplicateResourceException("El tipo de documento ya existe");
        }

        tipoDocumentoBD.setDescripcion(tipoDocumento.getDescripcion());

        return tipoDocumentoRepository.save(tipoDocumentoBD);
    }

    @Override
    public TipoDocumento eliminar(Integer id) {

        TipoDocumento tipoDocumento = buscarPorId(id);

        tipoDocumento.setEstado(false);

        return tipoDocumentoRepository.save(tipoDocumento);
    }

}
