package com.example.FinalProjectCrypto1.controller.gestion;

import com.example.FinalProjectCrypto1.model.gestion.Categoria;
import com.example.FinalProjectCrypto1.service.gestion.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PreAuthorize("@permisoService.tienePermiso('Categorias', 'VER')")
    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {
        return ResponseEntity.ok(categoriaService.listar());
    }

    @PreAuthorize("@permisoService.tienePermiso('Categorias', 'VER')")
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @PreAuthorize("@permisoService.tienePermiso('Categorias', 'CREAR')")
    @PostMapping
    public ResponseEntity<Categoria> guardar(@Valid @RequestBody Categoria categoria) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.guardar(categoria));
    }

    @PreAuthorize("@permisoService.tienePermiso('Categorias', 'EDITAR')")
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Categoria categoria) {

        return ResponseEntity.ok(categoriaService.actualizar(id, categoria));
    }

    @PreAuthorize("@permisoService.tienePermiso('Categorias', 'ELIMINAR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Categoria> eliminar(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.eliminar(id));
    }
}