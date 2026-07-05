package com.example.FinalProjectCrypto1.service.gestion;

import com.example.FinalProjectCrypto1.exception.DuplicateResourceException;
import com.example.FinalProjectCrypto1.exception.ResourceNotFoundException;
import com.example.FinalProjectCrypto1.model.gestion.Categoria;
import com.example.FinalProjectCrypto1.repository.gestion.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> listar() {
        return categoriaRepository.findByEstadoTrueOrderByNombreCategoriaAsc();
    }

    @Override
    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findByCodCategoriaAndEstadoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
    }

    @Override
    public Categoria guardar(Categoria categoria) {

        categoria.setNombreCategoria(categoria.getNombreCategoria().trim());

        if (categoriaRepository.findByNombreCategoriaIgnoreCase(categoria.getNombreCategoria()).isPresent()) {
            throw new DuplicateResourceException("La categoría ya existe");
        }

        categoria.setEstado(true);

        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria actualizar(Integer id, Categoria categoria) {

        Categoria categoriaBD = buscarPorId(id);

        categoria.setNombreCategoria(categoria.getNombreCategoria().trim());

        Optional<Categoria> existente =
                categoriaRepository.findByNombreCategoriaIgnoreCase(categoria.getNombreCategoria());

        if (existente.isPresent()
                && !existente.get().getCodCategoria().equals(id)) {

            throw new DuplicateResourceException("La categoría ya existe");
        }

        categoriaBD.setNombreCategoria(categoria.getNombreCategoria());

        return categoriaRepository.save(categoriaBD);
    }

    @Override
    public Categoria eliminar(Integer id) {

        Categoria categoria = buscarPorId(id);

        categoria.setEstado(false);

        return categoriaRepository.save(categoria);
    }
}