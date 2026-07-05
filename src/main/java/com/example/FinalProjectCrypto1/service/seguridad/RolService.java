package com.example.FinalProjectCrypto1.service.seguridad;

import com.example.FinalProjectCrypto1.model.seguridad.Rol;

import java.util.List;

public interface RolService {

    List<Rol> listar();

    Rol buscarPorId(Integer id);

    Rol guardar(Rol rol);

    Rol actualizar(Integer id, Rol rol);

    Rol eliminar(Integer id);
}