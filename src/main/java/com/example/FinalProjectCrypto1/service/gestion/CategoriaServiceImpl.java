package com.example.FinalProjectCrypto1.service.gestion;

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
        return categoriaRepository.findByEstadoTrueOrderByNombreAsc();
    }

    @Override
    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findByIdAndEstadoTrue(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    @Override
    public Categoria guardar(Categoria categoria) {

        categoria.setNombre(categoria.getNombre().trim());

        if (categoriaRepository.findByNombre(categoria.getNombre()).isPresent()) {
            throw new RuntimeException("La categoría ya existe");
        }

        categoria.setEstado(true);

        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria actualizar(Integer id, Categoria categoria) {

        Categoria categoriaBD = buscarPorId(id);

        categoria.setNombre(categoria.getNombre().trim());

        Optional<Categoria> existente =
                categoriaRepository.findByNombre(categoria.getNombre());

        if (existente.isPresent()
                && !existente.get().getIdCategoria().equals(id)) {

            throw new RuntimeException("La categoría ya existe");
        }

        categoriaBD.setNombre(categoria.getNombre());

        return categoriaRepository.save(categoriaBD);
    }

    @Override
    public Categoria eliminar(Integer id) {

        Categoria categoria = buscarPorId(id);

        categoria.setEstado(false);

        return categoriaRepository.save(categoria);
    }
}