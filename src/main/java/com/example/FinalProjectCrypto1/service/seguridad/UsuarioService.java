package com.example.FinalProjectCrypto1.service.seguridad;

import com.example.FinalProjectCrypto1.dto.seguridad.UsuarioDto;

import java.util.List;

public interface UsuarioService {

    List<UsuarioDto> listar();

    UsuarioDto buscarPorId(Integer id);

    UsuarioDto cambiarRol(Integer id, Integer idRol);

    UsuarioDto cambiarEstado(Integer id, Boolean estado);
}