package com.example.FinalProjectCrypto1.service.procesos;

import com.example.FinalProjectCrypto1.dto.procesos.IngresoProductoDTO;
import com.example.FinalProjectCrypto1.exception.ResourceNotFoundException;
import com.example.FinalProjectCrypto1.model.gestion.Producto;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.gestion.ProductoRepository;
import com.example.FinalProjectCrypto1.repository.seguridad.UsuarioRespository;
import com.example.FinalProjectCrypto1.service.auditoria.AuditoriaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IngresoProductoServiceImpl implements IngresoProductoService {

    private final ProductoRepository productoRepository;
    private final KardexService kardexService;
    private final UsuarioRespository usuarioRepository;
    private final AuditoriaService auditoriaService;
    private final HttpServletRequest httpRequest;

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

        // antes estaba hardcodeado: usuario.setIdUsuario(1)
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        Usuario usuario = usuarioRepository.findByUsuario(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        kardexService.registrarIngreso(
                producto,
                saldoInicialUnidad,
                saldoInicialFraccion,
                dto.getCantidadUnidad(),
                dto.getCantidadFraccion(),
                usuario,
                dto.getObservacion()
        );

        auditoriaService.registrar(
                usuario.getIdUsuario(),
                "Procesos",
                "producto",
                "INGRESO",
                producto.getCodProducto(),
                "unidad=" + saldoInicialUnidad + ", fraccion=" + saldoInicialFraccion,
                "unidad=" + producto.getCantidadUnidad() + ", fraccion=" + producto.getCantidadFraccion(),
                httpRequest
        );

    }

}