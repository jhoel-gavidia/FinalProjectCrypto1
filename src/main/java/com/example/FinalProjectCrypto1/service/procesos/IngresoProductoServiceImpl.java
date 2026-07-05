package com.example.FinalProjectCrypto1.service.procesos;

import com.example.FinalProjectCrypto1.dto.procesos.IngresoProductoDTO;
import com.example.FinalProjectCrypto1.exception.ResourceNotFoundException;
import com.example.FinalProjectCrypto1.model.gestion.Producto;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.gestion.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngresoProductoServiceImpl implements IngresoProductoService {

    private final ProductoRepository productoRepository;
    private final KardexService kardexService;

    @Override
    public void registrarIngreso(IngresoProductoDTO dto) {

        Producto producto = productoRepository
                .findByCodProductoAndEstadoTrue(dto.getCodProducto())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Producto no encontrado"));

        Integer saldoInicialUnidad = producto.getCantidadUnidad();
        Integer saldoInicialFraccion = producto.getCantidadFraccion();

        producto.setCantidadUnidad(
                producto.getCantidadUnidad() + dto.getCantidadUnidad()
        );

        producto.setCantidadFraccion(
                producto.getCantidadFraccion() + dto.getCantidadFraccion()
        );

        productoRepository.save(producto);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        kardexService.registrarIngreso(
                producto,
                saldoInicialUnidad,
                saldoInicialFraccion,
                dto.getCantidadUnidad(),
                dto.getCantidadFraccion(),
                usuario,
                dto.getObservacion()
        );

    }

}
