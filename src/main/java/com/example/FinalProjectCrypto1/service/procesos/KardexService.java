package com.example.FinalProjectCrypto1.service.procesos;

import com.example.FinalProjectCrypto1.model.gestion.Producto;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;

public interface KardexService {

    void registrarIngreso(
            Producto producto,
            Integer saldoInicialUnidad,
            Integer saldoInicialFraccion,
            Integer cantidadUnidad,
            Integer cantidadFraccion,
            Usuario usuario,
            String observacion
    );

    void registrarVenta(
            Producto producto,
            Integer saldoInicialUnidad,
            Integer saldoInicialFraccion,
            Integer cantidadUnidad,
            Integer cantidadFraccion,
            Usuario usuario,
            String documento
    );

}
