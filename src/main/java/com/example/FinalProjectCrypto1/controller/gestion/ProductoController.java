package com.example.FinalProjectCrypto1.controller.gestion;

import com.example.FinalProjectCrypto1.model.gestion.Producto;
import com.example.FinalProjectCrypto1.service.gestion.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PreAuthorize("@permisoService.tienePermiso('Productos', 'VER')")
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }

    @PreAuthorize("@permisoService.tienePermiso('Productos', 'VER')")
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    @PreAuthorize("@permisoService.tienePermiso('Productos', 'CREAR')")
    @PostMapping
    public ResponseEntity<Producto> guardar(@Valid @RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.guardar(producto));
    }

    @PreAuthorize("@permisoService.tienePermiso('Productos', 'EDITAR')")
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id,
                                               @Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizar(id, producto));
    }

    @PreAuthorize("@permisoService.tienePermiso('Productos', 'ELIMINAR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Producto> eliminar(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.eliminar(id));
    }

}