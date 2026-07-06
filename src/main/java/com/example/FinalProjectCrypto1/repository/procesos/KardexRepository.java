package com.example.FinalProjectCrypto1.repository.procesos;

import com.example.FinalProjectCrypto1.model.procesos.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KardexRepository extends JpaRepository<Kardex, Integer> {

    List<Kardex> findAllByOrderByFechaHoraDesc();

    List<Kardex> findByProducto_CodProductoOrderByFechaHoraDesc(Integer codProducto);
}