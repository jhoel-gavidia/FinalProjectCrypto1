package com.example.FinalProjectCrypto1.service.gestion;

import com.example.FinalProjectCrypto1.exception.DuplicateResourceException;
import com.example.FinalProjectCrypto1.exception.ResourceNotFoundException;
import com.example.FinalProjectCrypto1.model.gestion.Categoria;
import com.example.FinalProjectCrypto1.model.gestion.Producto;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.gestion.CategoriaRepository;
import com.example.FinalProjectCrypto1.repository.gestion.ProductoRepository;
import com.example.FinalProjectCrypto1.service.auditoria.AuditoriaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final AuditoriaService auditoriaService;
    private final HttpServletRequest httpRequest;

    private Integer usuarioActualId() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return usuario.getIdUsuario();
    }


    private String resumen(Producto p) {
        return "nombreProducto=" + p.getNombreProducto()
                + ", codCategoria=" + p.getCategoria().getCodCategoria()
                + ", precioUnitario=" + p.getPrecioUnitario()
                + ", precioFraccion=" + p.getPrecioFraccion()
                + ", cantidadUnidad=" + p.getCantidadUnidad()
                + ", cantidadFraccion=" + p.getCantidadFraccion()
                + ", estado=" + p.getEstado();
    }

    @Override
    public List<Producto> listar() {
        return productoRepository.findByEstadoTrueOrderByNombreProductoAsc();
    }

    @Override
    public Producto buscarPorId(Integer id) {
        return productoRepository.findByCodProductoAndEstadoTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Producto no encontrado"));
    }

    @Override
    public Producto guardar(Producto producto) {

        producto.setNombreProducto(producto.getNombreProducto().trim());

        if (productoRepository.findByNombreProductoIgnoreCase(producto.getNombreProducto()).isPresent()) {
            throw new DuplicateResourceException("El producto ya existe");
        }

        Categoria categoria = categoriaRepository
                .findByCodCategoriaAndEstadoTrue(producto.getCategoria().getCodCategoria())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Categoría no encontrada"));

        producto.setCategoria(categoria);
        producto.setEstado(true);

        Producto productoGuardado = productoRepository.save(producto);

        auditoriaService.registrar(
                usuarioActualId(),
                "Gestion",
                "producto",
                "INSERT",
                productoGuardado.getCodProducto(),
                null,
                resumen(productoGuardado),
                httpRequest
        );

        return productoGuardado;
    }

    @Override
    public Producto actualizar(Integer id, Producto producto) {

        Producto productoBD = buscarPorId(id);

        String snapshotAnterior = resumen(productoBD);

        producto.setNombreProducto(producto.getNombreProducto().trim());

        Optional<Producto> existente =
                productoRepository.findByNombreProductoIgnoreCase(producto.getNombreProducto());

        if (existente.isPresent()
                && !existente.get().getCodProducto().equals(id)) {

            throw new DuplicateResourceException("El producto ya existe");
        }

        Categoria categoria = categoriaRepository
                .findByCodCategoriaAndEstadoTrue(producto.getCategoria().getCodCategoria())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Categoría no encontrada"));

        productoBD.setNombreProducto(producto.getNombreProducto());
        productoBD.setPrecioUnitario(producto.getPrecioUnitario());
        productoBD.setPrecioFraccion(producto.getPrecioFraccion());
        productoBD.setCantidadUnidad(producto.getCantidadUnidad());
        productoBD.setCantidadFraccion(producto.getCantidadFraccion());
        productoBD.setCategoria(categoria);

        Producto productoActualizado = productoRepository.save(productoBD);

        auditoriaService.registrar(
                usuarioActualId(),
                "Gestion",
                "producto",
                "UPDATE",
                productoActualizado.getCodProducto(),
                snapshotAnterior,
                resumen(productoActualizado),
                httpRequest
        );

        return productoActualizado;
    }

    @Override
    public Producto eliminar(Integer id) {

        Producto producto = buscarPorId(id);

        producto.setEstado(false);

        Producto productoEliminado = productoRepository.save(producto);

        auditoriaService.registrar(
                usuarioActualId(),
                "Gestion",
                "producto",
                "DELETE",
                productoEliminado.getCodProducto(),
                "estado: true",
                "estado: false",
                httpRequest
        );

        return productoEliminado;
    }

}