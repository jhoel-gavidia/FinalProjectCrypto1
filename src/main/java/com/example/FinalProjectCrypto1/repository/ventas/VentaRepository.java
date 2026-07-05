package com.example.FinalProjectCrypto1.repository.ventas;

import com.example.FinalProjectCrypto1.model.ventas.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta,Integer> {
    Optional<Venta> findByCodVentaAndEstadoTrue(Integer codVenta);
}
