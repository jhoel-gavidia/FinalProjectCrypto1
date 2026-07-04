package com.example.FinalProjectCrypto1.service.gestion;

import com.example.FinalProjectCrypto1.model.gestion.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> listar();

    Producto buscarPorId(Integer id);

    Producto guardar(Producto producto);

    Producto actualizar(Integer id, Producto producto);

    Producto eliminar(Integer id);

}
