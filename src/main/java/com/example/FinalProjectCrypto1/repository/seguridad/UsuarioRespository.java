package com.example.FinalProjectCrypto1.repository.seguridad;

import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRespository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsuario(String usuario);

    long countByRol_NombreRolIgnoreCaseAndEstadoTrue(String nombreRol);
}