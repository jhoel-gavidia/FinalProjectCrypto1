package com.example.FinalProjectCrypto1.service.procesos;

import com.example.FinalProjectCrypto1.model.gestion.Producto;
import com.example.FinalProjectCrypto1.model.procesos.Kardex;
import com.example.FinalProjectCrypto1.model.procesos.TipoOperacion;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.procesos.KardexRepository;
import com.example.FinalProjectCrypto1.repository.procesos.TipoOperacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KardexServiceImpl implements KardexService {

    private final KardexRepository kardexRepository;
    private final TipoOperacionRepository tipoOperacionRepository;

    @Override
    public void registrarIngreso(
            Producto producto,
            Integer saldoInicialUnidad,
            Integer saldoInicialFraccion,
            Integer cantidadUnidad,
            Integer cantidadFraccion,
            Usuario usuario,
            String observacion) {

        TipoOperacion tipoOperacion = tipoOperacionRepository
                .findById(1)
                .orElseThrow(() -> new RuntimeException("Tipo de operación no encontrado"));

        Kardex kardex = new Kardex();

        kardex.setProducto(producto);
        kardex.setTipoOperacion(tipoOperacion);

        kardex.setCantidadInicial(saldoInicialUnidad);

        kardex.setCantidadMovimiento(cantidadUnidad);

        kardex.setCantidadFinal(producto.getCantidadUnidad());

        kardex.setSaldoUnitario(producto.getCantidadUnidad());

        kardex.setSaldoFraccionario(producto.getCantidadFraccion());

        kardex.setFechaHora(LocalDateTime.now());

        kardex.setCodDocumento(null);

        kardex.setObservacion(observacion);

        kardex.setEstado(true);

        kardex.setFechaRegistro(LocalDateTime.now());

        kardex.setUsuario(usuario);

        kardexRepository.save(kardex);
    }

    @Override
    public void registrarVenta(
            Producto producto,
            Integer saldoInicialUnidad,
            Integer saldoInicialFraccion,
            Integer cantidadUnidad,
            Integer cantidadFraccion,
            Usuario usuario,
            String documento) {

        TipoOperacion tipoOperacion = tipoOperacionRepository
                .findById(2)
                .orElseThrow(() -> new RuntimeException("Tipo de operación no encontrado"));

        Kardex kardex = new Kardex();

        kardex.setProducto(producto);
        kardex.setTipoOperacion(tipoOperacion);

        // Saldo antes de la venta
        kardex.setCantidadInicial(saldoInicialUnidad);

        // Cantidad vendida
        kardex.setCantidadMovimiento(cantidadUnidad);

        // Saldo después de la venta
        kardex.setCantidadFinal(producto.getCantidadUnidad());

        kardex.setSaldoUnitario(producto.getCantidadUnidad());

        kardex.setSaldoFraccionario(producto.getCantidadFraccion());

        kardex.setFechaHora(LocalDateTime.now());

        kardex.setCodDocumento(documento);

        kardex.setObservacion("Venta");

        kardex.setEstado(true);

        kardex.setFechaRegistro(LocalDateTime.now());

        kardex.setUsuario(usuario);

        kardexRepository.save(kardex);

    }

    @Override
    public void registrarExtorno(
            Producto producto,
            Integer saldoInicialUnidad,
            Integer saldoInicialFraccion,
            Integer cantidadUnidad,
            Integer cantidadFraccion,
            Usuario usuario,
            String documento) {

        TipoOperacion tipoOperacion = tipoOperacionRepository
                .findById(3)
                .orElseThrow(() -> new RuntimeException("Tipo de operación no encontrado"));

        Kardex kardex = new Kardex();

        kardex.setProducto(producto);
        kardex.setTipoOperacion(tipoOperacion);

        kardex.setCantidadInicial(saldoInicialUnidad);

        kardex.setCantidadMovimiento(cantidadUnidad);


        kardex.setCantidadFinal(producto.getCantidadUnidad());

        kardex.setSaldoUnitario(producto.getCantidadUnidad());

        kardex.setSaldoFraccionario(producto.getCantidadFraccion());

        kardex.setFechaHora(LocalDateTime.now());

        kardex.setCodDocumento(documento);

        kardex.setObservacion("Extorno");

        kardex.setEstado(true);

        kardex.setFechaRegistro(LocalDateTime.now());

        kardex.setUsuario(usuario);

        kardexRepository.save(kardex);
    }

    @Override
    public void registrarAjuste(
            Producto producto,
            Integer saldoInicialUnidad,
            Integer saldoInicialFraccion,
            Integer cantidadUnidad,
            Integer cantidadFraccion,
            Usuario usuario,
            String observacion) {

        TipoOperacion tipoOperacion = tipoOperacionRepository
                .findById(4)
                .orElseThrow(() -> new RuntimeException("Tipo de operación no encontrado"));

        Kardex kardex = new Kardex();

        kardex.setProducto(producto);
        kardex.setTipoOperacion(tipoOperacion);

        kardex.setCantidadInicial(saldoInicialUnidad);


        kardex.setCantidadMovimiento(cantidadUnidad);

        kardex.setCantidadFinal(producto.getCantidadUnidad());

        kardex.setSaldoUnitario(producto.getCantidadUnidad());

        kardex.setSaldoFraccionario(producto.getCantidadFraccion());

        kardex.setFechaHora(LocalDateTime.now());

        kardex.setCodDocumento(null);

        kardex.setObservacion(observacion);

        kardex.setEstado(true);

        kardex.setFechaRegistro(LocalDateTime.now());

        kardex.setUsuario(usuario);

        kardexRepository.save(kardex);
    }
}
