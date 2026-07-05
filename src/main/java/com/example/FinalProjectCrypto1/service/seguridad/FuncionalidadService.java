package com.example.FinalProjectCrypto1.service.seguridad;

import com.example.FinalProjectCrypto1.dto.seguridad.FuncionalidadNodoDto;
import com.example.FinalProjectCrypto1.model.seguridad.Funcionalidad;

import java.util.List;

public interface FuncionalidadService {

    List<Funcionalidad> listar();

    List<FuncionalidadNodoDto> obtenerArbol();

    Funcionalidad buscarPorId(Integer id);

    Funcionalidad guardar(Funcionalidad funcionalidad);

    Funcionalidad actualizar(Integer id, Funcionalidad funcionalidad);

    void eliminar(Integer id);
}