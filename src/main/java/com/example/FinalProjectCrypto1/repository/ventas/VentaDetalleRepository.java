package com.example.FinalProjectCrypto1.repository.ventas;

import com.example.FinalProjectCrypto1.model.ventas.VentaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VentaDetalleRepository extends JpaRepository<VentaDetalle,Integer> {
    List<VentaDetalle> findByVenta_CodVenta(Integer codVenta);
}
