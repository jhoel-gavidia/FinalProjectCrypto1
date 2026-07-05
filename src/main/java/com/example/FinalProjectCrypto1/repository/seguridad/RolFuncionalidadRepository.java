package com.example.FinalProjectCrypto1.repository.seguridad;

import com.example.FinalProjectCrypto1.model.seguridad.RolFuncionalidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolFuncionalidadRepository extends JpaRepository<RolFuncionalidad, Integer> {

    List<RolFuncionalidad> findByIdRol_IdRol(Integer idRol);

    void deleteByIdRol_IdRol(Integer idRol);

    Optional<RolFuncionalidad> findByIdRol_IdRolAndIdFuncionalidad_NombreIgnoreCase(
            Integer idRol, String nombreFuncionalidad);
}