package com.example.FinalProjectCrypto1.service.gestion;

import com.example.FinalProjectCrypto1.exception.DuplicateResourceException;
import com.example.FinalProjectCrypto1.exception.ResourceNotFoundException;
import com.example.FinalProjectCrypto1.model.gestion.Categoria;
import com.example.FinalProjectCrypto1.model.gestion.Producto;
import com.example.FinalProjectCrypto1.repository.gestion.CategoriaRepository;
import com.example.FinalProjectCrypto1.repository.gestion.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public List<Producto> listar() {
        return productoRepository.findByEstadoTrueOrderByNombreAsc();
    }

    @Override
    public Producto buscarPorId(Integer id) {
        return productoRepository.findByIdAndEstadoTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Producto no encontrado"));
    }

    @Override
    public Producto guardar(Producto producto) {

        producto.setNombre(producto.getNombre().trim());

        if (productoRepository.findByNombre(producto.getNombre()).isPresent()) {
            throw new DuplicateResourceException("El producto ya existe");
        }

        Categoria categoria = categoriaRepository
                .findByIdAndEstadoTrue(producto.getCategoria().getIdCategoria())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Categoría no encontrada"));

        producto.setCategoria(categoria);
        producto.setEstado(true);

        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Integer id, Producto producto) {

        Producto productoBD = buscarPorId(id);

        producto.setNombre(producto.getNombre().trim());

        Optional<Producto> existente =
                productoRepository.findByNombre(producto.getNombre());

        if (existente.isPresent()
                && !existente.get().getIdProducto().equals(id)) {

            throw new DuplicateResourceException("El producto ya existe");
        }

        Categoria categoria = categoriaRepository
                .findByIdAndEstadoTrue(producto.getCategoria().getIdCategoria())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Categoría no encontrada"));

        productoBD.setNombre(producto.getNombre());
        productoBD.setDescripcion(producto.getDescripcion());
        productoBD.setPrecio(producto.getPrecio());
        productoBD.setStock(producto.getStock());
        productoBD.setCategoria(categoria);

        return productoRepository.save(productoBD);
    }

    @Override
    public Producto eliminar(Integer id) {

        Producto producto = buscarPorId(id);

        producto.setEstado(false);

        return productoRepository.save(producto);
    }

}
