package com.example.FinalProjectCrypto1.repository.seguridad;

import com.example.FinalProjectCrypto1.model.seguridad.Funcionalidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionalidadRepository extends JpaRepository<Funcionalidad, Integer> {

    List<Funcionalidad> findAllByOrderByNombreAsc();

    List<Funcionalidad> findByPadreIsNullOrderByNombreAsc();

    List<Funcionalidad> findByPadre_IdFuncionalidadOrderByNombreAsc(Integer idPadre);

    Optional<Funcionalidad> findByNombreIgnoreCase(String nombre);
}
