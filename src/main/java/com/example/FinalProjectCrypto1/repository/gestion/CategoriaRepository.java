package com.example.FinalProjectCrypto1.repository.gestion;

import com.example.FinalProjectCrypto1.model.gestion.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByEstadoTrueOrderByNombreCategoriaAsc();

    Optional<Categoria> findByNombreCategoriaIgnoreCase(String nombreCategoria);

    Optional<Categoria> findByCodCategoriaAndEstadoTrue(Integer codCategoria);
}
