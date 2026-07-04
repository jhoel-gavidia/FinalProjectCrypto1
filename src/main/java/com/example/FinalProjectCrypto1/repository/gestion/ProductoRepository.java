package com.example.FinalProjectCrypto1.repository.gestion;

import com.example.FinalProjectCrypto1.model.gestion.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByEstadoTrueOrderByNombreAsc();

    Optional<Producto> findByNombre(String nombre);

    Optional<Producto> findByIdAndEstadoTrue(Integer id);

}
