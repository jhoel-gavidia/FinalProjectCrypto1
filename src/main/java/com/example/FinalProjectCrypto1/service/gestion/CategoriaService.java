package com.example.FinalProjectCrypto1.service.gestion;

import com.example.FinalProjectCrypto1.model.gestion.Categoria;

import java.util.List;

public interface CategoriaService {

    List<Categoria> listar();

    Categoria buscarPorId(Integer id);

    Categoria guardar(Categoria categoria);

    Categoria actualizar(Integer id, Categoria categoria);

    Categoria eliminar(Integer id);

}
