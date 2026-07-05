package com.example.FinalProjectCrypto1.service.gestion;

import com.example.FinalProjectCrypto1.model.gestion.TipoDocumento;

import java.util.List;

public interface TipoDocumentoService {

    List<TipoDocumento> listar();

    TipoDocumento buscarPorId(Integer id);

    TipoDocumento guardar(TipoDocumento tipoDocumento);

    TipoDocumento actualizar(Integer id, TipoDocumento tipoDocumento);

    TipoDocumento eliminar(Integer id);

}
