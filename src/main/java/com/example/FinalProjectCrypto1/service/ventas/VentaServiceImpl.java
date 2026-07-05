package com.example.FinalProjectCrypto1.service.ventas;

import com.example.FinalProjectCrypto1.dto.ventas.ExtornoRequest;
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
import com.example.FinalProjectCrypto1.repository.seguridad.UsuarioRespository;
import com.example.FinalProjectCrypto1.repository.ventas.VentaDetalleRepository;
import com.example.FinalProjectCrypto1.repository.ventas.VentaRepository;
import com.example.FinalProjectCrypto1.service.auditoria.AuditoriaService;
import com.example.FinalProjectCrypto1.service.procesos.KardexService;
import com.example.FinalProjectCrypto1.twofactor.GoogleAuthenticatorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

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
    private final GoogleAuthenticatorService googleAuthenticatorService;
    private final UsuarioRespository usuarioRepository;
    private final AuditoriaService auditoriaService;
    private final HttpServletRequest httpRequest;

    @Override
    public void registrarVenta(VentaRequest request) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        boolean valido = googleAuthenticatorService.verificarCodigo(
                usuario.getSecretKey(),
                request.getCodigoGoogle()
        );

        if (!valido) {
            throw new RuntimeException("Código de Google Authenticator inválido.");
        }

        Cliente cliente = clienteRepository
                .findByCodClienteAndEstadoTrue(request.getCodCliente())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente no encontrado"));

        validarTipoDocumento(cliente, request.getTipoDocumento());

        String serie = request.getTipoDocumento().equalsIgnoreCase("FACTURA")
                ? "F001"
                : "B001";

        String numeroDocumento = correlativoService.generarCorrelativo(serie);

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setTipoDocumento(request.getTipoDocumento());
        venta.setNumeroDocumento(numeroDocumento);
        venta.setFechaHora(LocalDateTime.now());
        venta.setEstado(true);
        venta.setSubtotal(BigDecimal.ZERO);
        venta.setIgv(BigDecimal.ZERO);
        venta.setTotal(BigDecimal.ZERO);

        venta = ventaRepository.save(venta);

        BigDecimal subtotal = BigDecimal.ZERO;

        final int FRACCIONES_POR_UNIDAD = 10;

        for (VentaDetalleRequest detalle : request.getDetalles()) {

            Producto producto = productoRepository
                    .findByCodProductoAndEstadoTrue(detalle.getCodProducto())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Producto no encontrado"));

            if (detalle.getCantidadUnidad() == 0 &&
                    detalle.getCantidadFraccion() == 0) {
                throw new RuntimeException("Debe vender al menos una unidad o una fracción.");
            }

            int saldoInicialUnidad = producto.getCantidadUnidad();
            int saldoInicialFraccion = producto.getCantidadFraccion();

            // Validar unidades
            if (producto.getCantidadUnidad() < detalle.getCantidadUnidad()) {
                throw new RuntimeException(
                        "Stock insuficiente para el producto: "
                                + producto.getNombreProducto());
            }

            producto.setCantidadUnidad(
                    producto.getCantidadUnidad() - detalle.getCantidadUnidad()
            );

            // Manejo de fracciones
            int fraccionesRestantes =
                    producto.getCantidadFraccion() - detalle.getCantidadFraccion();

            while (fraccionesRestantes < 0) {

                if (producto.getCantidadUnidad() <= 0) {
                    throw new RuntimeException(
                            "Stock insuficiente de fracciones para el producto: "
                                    + producto.getNombreProducto());
                }

                producto.setCantidadUnidad(
                        producto.getCantidadUnidad() - 1
                );

                fraccionesRestantes += FRACCIONES_POR_UNIDAD;
            }

            producto.setCantidadFraccion(fraccionesRestantes);

            productoRepository.save(producto);

            BigDecimal subtotalDetalle = BigDecimal.ZERO;

            if (detalle.getCantidadUnidad() > 0) {

                subtotalDetalle = subtotalDetalle.add(
                        producto.getPrecioUnitario().multiply(
                                BigDecimal.valueOf(detalle.getCantidadUnidad())
                        )
                );

            }

            if (detalle.getCantidadFraccion() > 0) {

                subtotalDetalle = subtotalDetalle.add(
                        producto.getPrecioFraccion().multiply(
                                BigDecimal.valueOf(detalle.getCantidadFraccion())
                        )
                );

            }

            subtotal = subtotal.add(subtotalDetalle);

            VentaDetalle ventaDetalle = new VentaDetalle();

            ventaDetalle.setVenta(venta);
            ventaDetalle.setProducto(producto);
            ventaDetalle.setCantidadUnidad(detalle.getCantidadUnidad());
            ventaDetalle.setCantidadFraccion(detalle.getCantidadFraccion());

            if (detalle.getCantidadUnidad() > 0) {
                ventaDetalle.setPrecio(producto.getPrecioUnitario());
            } else {
                ventaDetalle.setPrecio(producto.getPrecioFraccion());
            }

            ventaDetalle.setSubtotal(subtotalDetalle);

            ventaDetalleRepository.save(ventaDetalle);

            kardexService.registrarVenta(
                    producto,
                    saldoInicialUnidad,
                    saldoInicialFraccion,
                    detalle.getCantidadUnidad(),
                    detalle.getCantidadFraccion(),
                    usuario,
                    numeroDocumento
            );
        }

        if (request.getTipoDocumento().equalsIgnoreCase("FACTURA")) {

            BigDecimal igv = subtotal
                    .multiply(new BigDecimal("0.18"))
                    .setScale(2, RoundingMode.HALF_UP);

            venta.setSubtotal(subtotal);
            venta.setIgv(igv);
            venta.setTotal(subtotal.add(igv));

        } else {

            venta.setSubtotal(BigDecimal.ZERO);
            venta.setIgv(BigDecimal.ZERO);
            venta.setTotal(subtotal);

        }

        Venta ventaFinal = ventaRepository.save(venta);

        auditoriaService.registrar(
                usuario.getIdUsuario(),
                "Ventas",
                "venta",
                "INSERT",
                ventaFinal.getCodVenta(),
                null,
                "documento=" + ventaFinal.getNumeroDocumento()
                        + ", cliente=" + cliente.getCodCliente()
                        + ", tipoDocumento=" + ventaFinal.getTipoDocumento()
                        + ", total=" + ventaFinal.getTotal(),
                httpRequest
        );
    }

    @Override
    public void extornarVenta(ExtornoRequest request) {

        Venta venta = ventaRepository.findByCodVentaAndEstadoTrue(request.getCodVenta())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Venta no encontrada o ya fue extornada"));

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        Usuario usuario = usuarioRepository.findByUsuario(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<VentaDetalle> detalles = ventaDetalleRepository.findByVenta_CodVenta(venta.getCodVenta());

        for (VentaDetalle detalle : detalles) {

            Producto producto = detalle.getProducto();

            int saldoInicialUnidad = producto.getCantidadUnidad();
            int saldoInicialFraccion = producto.getCantidadFraccion();

            producto.setCantidadUnidad(
                    producto.getCantidadUnidad() + detalle.getCantidadUnidad()
            );

            producto.setCantidadFraccion(
                    producto.getCantidadFraccion() + detalle.getCantidadFraccion()
            );

            productoRepository.save(producto);

            kardexService.registrarExtorno(
                    producto,
                    saldoInicialUnidad,
                    saldoInicialFraccion,
                    detalle.getCantidadUnidad(),
                    detalle.getCantidadFraccion(),
                    usuario,
                    venta.getNumeroDocumento()
            );
        }

        venta.setEstado(false);

        Venta ventaExtornada = ventaRepository.save(venta);

        auditoriaService.registrar(
                usuario.getIdUsuario(),
                "Ventas",
                "venta",
                "EXTORNO",
                ventaExtornada.getCodVenta(),
                "estado: true",
                "estado: false" + (request.getObservacion() != null
                        ? ", observacion=" + request.getObservacion()
                        : ""),
                httpRequest
        );
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