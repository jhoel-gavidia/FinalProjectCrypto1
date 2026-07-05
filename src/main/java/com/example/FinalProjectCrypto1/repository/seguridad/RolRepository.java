package com.example.FinalProjectCrypto1.repository.seguridad;

import com.example.FinalProjectCrypto1.model.seguridad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    List<Rol> findByEstadoTrueOrderByNombreRolAsc();

    Optional<Rol> findByIdRolAndEstadoTrue(Integer idRol);

    Optional<Rol> findByNombreRolIgnoreCase(String nombreRol);
}
