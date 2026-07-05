package com.example.FinalProjectCrypto1.service.ventas;

import com.example.FinalProjectCrypto1.dto.ventas.VentaDetalleRequest;
import com.example.FinalProjectCrypto1.dto.ventas.VentaRequest;
import com.example.FinalProjectCrypto1.exception.ResourceNotFoundException;
import com.example.FinalProjectCrypto1.model.gestion.Cliente;
import com.example.FinalProjectCrypto1.model.gestion.Producto;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.model.ventas.Venta;
import com.example.FinalProjectCrypto1.model.ventas.VentaDetalle;
import com.example.FinalProjectCrypto1.repository.gestion.ClienteRepository;
import com.example.FinalProjectCrypto1.repository.gestion.ProductoRepository;
import com.example.FinalProjectCrypto1.repository.ventas.VentaDetalleRepository;
import com.example.FinalProjectCrypto1.repository.ventas.VentaRepository;
import com.example.FinalProjectCrypto1.service.procesos.KardexService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final VentaDetalleRepository ventaDetalleRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final CorrelativoService correlativoService;
    private final KardexService kardexService;

    @Override
    public void registrarVenta(VentaRequest request) {

        Cliente cliente = clienteRepository
                .findByCodClienteAndEstadoTrue(request.getCodCliente())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente no encontrado"));

        validarTipoDocumento(cliente, request.getTipoDocumento());

        String serie = request.getTipoDocumento().equalsIgnoreCase("FACTURA")
                ? "F001"
                : "B001";

        String numeroDocumento =
                correlativoService.generarCorrelativo(serie);

        Venta venta = new Venta();

        venta.setCliente(cliente);
        venta.setTipoDocumento(request.getTipoDocumento());
        venta.setNumeroDocumento(numeroDocumento);
        venta.setFechaHora(LocalDateTime.now());
        venta.setEstado(true);

        BigDecimal subtotal = BigDecimal.ZERO;

        venta = ventaRepository.save(venta);

        for (VentaDetalleRequest detalle : request.getDetalles()) {

            Producto producto = productoRepository
                    .findByCodProductoAndEstadoTrue(detalle.getCodProducto())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Producto no encontrado"));

            if (producto.getCantidadUnidad() < detalle.getCantidadUnidad()) {
                throw new RuntimeException("Stock insuficiente");
            }

            producto.setCantidadUnidad(
                    producto.getCantidadUnidad() - detalle.getCantidadUnidad()
            );

            producto.setCantidadFraccion(
                    producto.getCantidadFraccion() - detalle.getCantidadFraccion()
            );

            productoRepository.save(producto);

            BigDecimal precio = producto.getPrecioUnitario();

            BigDecimal sub = precio.multiply(
                    BigDecimal.valueOf(detalle.getCantidadUnidad())
            );

            subtotal = subtotal.add(sub);

            VentaDetalle ventaDetalle = new VentaDetalle();

            ventaDetalle.setVenta(venta);
            ventaDetalle.setProducto(producto);
            ventaDetalle.setCantidadUnidad(detalle.getCantidadUnidad());
            ventaDetalle.setCantidadFraccion(detalle.getCantidadFraccion());
            ventaDetalle.setPrecio(precio);
            ventaDetalle.setSubtotal(sub);

            ventaDetalleRepository.save(ventaDetalle);

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(1);

            kardexService.registrarVenta(
                    producto,
                    detalle.getCantidadUnidad(),
                    detalle.getCantidadFraccion(),
                    usuario,
                    numeroDocumento
            );

        }

        if (request.getTipoDocumento().equalsIgnoreCase("FACTURA")) {

            BigDecimal igv = subtotal.multiply(new BigDecimal("0.18"))
                    .setScale(2, RoundingMode.HALF_UP);

            venta.setSubtotal(subtotal);
            venta.setIgv(igv);
            venta.setTotal(subtotal.add(igv));

        } else {

            venta.setSubtotal(BigDecimal.ZERO);
            venta.setIgv(BigDecimal.ZERO);
            venta.setTotal(subtotal);

        }

        ventaRepository.save(venta);

    }

    private void validarTipoDocumento(Cliente cliente, String tipoDocumento) {

        String documento = cliente.getTipoDocumento()
                .getDescripcion()
                .toUpperCase();

        if (tipoDocumento.equalsIgnoreCase("FACTURA")
                && !documento.equals("RUC")) {

            throw new RuntimeException(
                    "Las facturas solo pueden emitirse con RUC");
        }

        if (tipoDocumento.equalsIgnoreCase("BOLETA")
                && !documento.equals("DNI")) {

            throw new RuntimeException(
                    "Las boletas solo pueden emitirse con DNI");
        }

    }

}
