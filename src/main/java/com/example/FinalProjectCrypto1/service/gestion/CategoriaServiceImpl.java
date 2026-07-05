package com.example.FinalProjectCrypto1.service.gestion;

import com.example.FinalProjectCrypto1.exception.DuplicateResourceException;
import com.example.FinalProjectCrypto1.exception.ResourceNotFoundException;
import com.example.FinalProjectCrypto1.model.gestion.Categoria;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.gestion.CategoriaRepository;
import com.example.FinalProjectCrypto1.service.auditoria.AuditoriaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final AuditoriaService auditoriaService;
    private final HttpServletRequest httpRequest;

    private Integer usuarioActualId() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return usuario.getIdUsuario();
    }

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

        Categoria categoriaGuardada = categoriaRepository.save(categoria);

        auditoriaService.registrar(
                usuarioActualId(),
                "Gestion",
                "categoria",
                "INSERT",
                categoriaGuardada.getCodCategoria(),
                null,
                categoriaGuardada,
                httpRequest
        );

        return categoriaGuardada;
    }

    @Override
    public Categoria actualizar(Integer id, Categoria categoria) {

        Categoria categoriaBD = buscarPorId(id);

        // snapshot ANTES de modificar nada
        Categoria snapshotAnterior = new Categoria();
        snapshotAnterior.setCodCategoria(categoriaBD.getCodCategoria());
        snapshotAnterior.setNombreCategoria(categoriaBD.getNombreCategoria());
        snapshotAnterior.setEstado(categoriaBD.getEstado());

        categoria.setNombreCategoria(categoria.getNombreCategoria().trim());

        Optional<Categoria> existente =
                categoriaRepository.findByNombreCategoriaIgnoreCase(categoria.getNombreCategoria());

        if (existente.isPresent()
                && !existente.get().getCodCategoria().equals(id)) {

            throw new DuplicateResourceException("La categoría ya existe");
        }

        categoriaBD.setNombreCategoria(categoria.getNombreCategoria());

        Categoria categoriaActualizada = categoriaRepository.save(categoriaBD);

        auditoriaService.registrar(
                usuarioActualId(),
                "Gestion",
                "categoria",
                "UPDATE",
                categoriaActualizada.getCodCategoria(),
                snapshotAnterior,
                categoriaActualizada,
                httpRequest
        );

        return categoriaActualizada;
    }

    @Override
    public Categoria eliminar(Integer id) {

        Categoria categoria = buscarPorId(id);

        Categoria snapshotAnterior = new Categoria();

        snapshotAnterior.setCodCategoria(categoria.getCodCategoria());
        snapshotAnterior.setNombreCategoria(categoria.getNombreCategoria());
        snapshotAnterior.setEstado(categoria.getEstado());

        categoria.setEstado(false);

        Categoria categoriaEliminada = categoriaRepository.save(categoria);

        auditoriaService.registrar(
                usuarioActualId(),
                "Gestion",
                "categoria",
                "DELETE",
                categoriaEliminada.getCodCategoria(),
                snapshotAnterior,
                categoriaEliminada,
                httpRequest
        );

        return categoriaEliminada;
    }
}