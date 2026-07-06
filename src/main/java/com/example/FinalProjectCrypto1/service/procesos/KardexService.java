package com.example.FinalProjectCrypto1.service.procesos;

import com.example.FinalProjectCrypto1.model.gestion.Producto;
import com.example.FinalProjectCrypto1.model.procesos.Kardex;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;

import java.util.List;

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

    void registrarExtorno(
            Producto producto,
            Integer saldoInicialUnidad,
            Integer saldoInicialFraccion,
            Integer cantidadUnidad,
            Integer cantidadFraccion,
            Usuario usuario,
            String documento
    );

    void registrarAjuste(
            Producto producto,
            Integer saldoInicialUnidad,
            Integer saldoInicialFraccion,
            Integer cantidadUnidad,
            Integer cantidadFraccion,
            Usuario usuario,
            String observacion
    );

    List<Kardex> listar();

    List<Kardex> listarPorProducto(Integer codProducto);
}
