package com.example.FinalProjectCrypto1.service.procesos;

import com.example.FinalProjectCrypto1.dto.procesos.AjusteProductoDTO;
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

@Service
@RequiredArgsConstructor
public class AjusteProductoServiceImpl implements AjusteProductoService {

    private final ProductoRepository productoRepository;
    private final KardexService kardexService;
    private final UsuarioRespository usuarioRepository;
    private final AuditoriaService auditoriaService;
    private final HttpServletRequest httpRequest;

    @Override
    public void registrarAjuste(AjusteProductoDTO dto) {

        Producto producto = productoRepository
                .findByCodProductoAndEstadoTrue(dto.getCodProducto())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Producto no encontrado"));

        Integer saldoInicialUnidad = producto.getCantidadUnidad();
        Integer saldoInicialFraccion = producto.getCantidadFraccion();

        int movimientoUnidad = dto.getCantidadUnidadNueva() - saldoInicialUnidad;
        int movimientoFraccion = dto.getCantidadFraccionNueva() - saldoInicialFraccion;

        if (movimientoUnidad == 0 && movimientoFraccion == 0) {
            throw new RuntimeException(
                    "No hay ninguna diferencia que ajustar para este producto");
        }

        producto.setCantidadUnidad(dto.getCantidadUnidadNueva());
        producto.setCantidadFraccion(dto.getCantidadFraccionNueva());

        productoRepository.save(producto);

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        Usuario usuario = usuarioRepository.findByUsuario(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        kardexService.registrarAjuste(
                producto,
                saldoInicialUnidad,
                saldoInicialFraccion,
                movimientoUnidad,
                movimientoFraccion,
                usuario,
                dto.getObservacion()
        );

        auditoriaService.registrar(
                usuario.getIdUsuario(),
                "Procesos",
                "producto",
                "AJUSTE",
                producto.getCodProducto(),
                "unidad=" + saldoInicialUnidad + ", fraccion=" + saldoInicialFraccion,
                "unidad=" + producto.getCantidadUnidad() + ", fraccion=" + producto.getCantidadFraccion()
                        + " (motivo: " + dto.getObservacion() + ")",
                httpRequest
        );

    }

}