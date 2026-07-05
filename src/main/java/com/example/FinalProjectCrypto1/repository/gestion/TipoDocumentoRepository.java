package com.example.FinalProjectCrypto1.repository.gestion;

import com.example.FinalProjectCrypto1.model.gestion.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {

    List<TipoDocumento> findByEstadoTrueOrderByDescripcionAsc();

    Optional<TipoDocumento> findByDescripcionIgnoreCase(String descripcion);

    Optional<TipoDocumento> findByCodTipoDocumentoAndEstadoTrue(Integer codTipoDocumento);

}
