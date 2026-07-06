package com.example.FinalProjectCrypto1.controller.gestion;

import com.example.FinalProjectCrypto1.dto.gestion.ProductoResponseDto;
import com.example.FinalProjectCrypto1.model.gestion.Producto;
import com.example.FinalProjectCrypto1.service.gestion.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    private ProductoResponseDto toDto(Producto producto) {
        return new ProductoResponseDto(
                producto.getCodProducto(),
                producto.getNombreProducto(),
                producto.getCategoria().getCodCategoria(),
                producto.getCategoria().getNombreCategoria(),
                producto.getPrecioUnitario(),
                producto.getPrecioFraccion(),
                producto.getCantidadUnidad(),
                producto.getCantidadFraccion(),
                producto.getEstado()
        );
    }

    @PreAuthorize("@permisoService.tienePermiso('Productos', 'VER')")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> listar() {
        List<ProductoResponseDto> productos = productoService.listar().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }

    @PreAuthorize("@permisoService.tienePermiso('Productos', 'VER')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(toDto(productoService.buscarPorId(id)));
    }

    @PreAuthorize("@permisoService.tienePermiso('Productos', 'CREAR')")
    @PostMapping
    public ResponseEntity<ProductoResponseDto> guardar(@Valid @RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toDto(productoService.guardar(producto)));
    }

    @PreAuthorize("@permisoService.tienePermiso('Productos', 'EDITAR')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> actualizar(@PathVariable Integer id,
                                                          @Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(toDto(productoService.actualizar(id, producto)));
    }

    @PreAuthorize("@permisoService.tienePermiso('Productos', 'ELIMINAR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> eliminar(@PathVariable Integer id) {
        return ResponseEntity.ok(toDto(productoService.eliminar(id)));
    }

}