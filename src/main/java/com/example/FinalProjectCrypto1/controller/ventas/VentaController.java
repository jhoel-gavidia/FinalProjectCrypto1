package com.example.FinalProjectCrypto1.controller.ventas;

import com.example.FinalProjectCrypto1.dto.ventas.ExtornoRequest;
import com.example.FinalProjectCrypto1.dto.ventas.VentaRequest;
import com.example.FinalProjectCrypto1.model.ventas.Venta;
import com.example.FinalProjectCrypto1.service.ventas.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PreAuthorize("@permisoService.tienePermiso('Registrar Venta', 'CREAR')")
    @PostMapping
    public ResponseEntity<String> registrarVenta(
            @Valid @RequestBody VentaRequest request) {

        ventaService.registrarVenta(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Venta registrada correctamente");
    }

    @PreAuthorize("@permisoService.tienePermiso('Anular Venta', 'ELIMINAR')")
    @PostMapping("/extorno")
    public ResponseEntity<String> extornarVenta(
            @Valid @RequestBody ExtornoRequest request) {

        ventaService.extornarVenta(request);

        return ResponseEntity.ok("Venta extornada correctamente.");

    }

    @PreAuthorize("@permisoService.tienePermiso('Ver Ventas', 'VER')")
    @GetMapping
    public ResponseEntity<List<Venta>> listar() {
        return ResponseEntity.ok(ventaService.listar());
    }

    @PreAuthorize("@permisoService.tienePermiso('Ver Ventas', 'VER')")
    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ventaService.buscarPorId(id));
    }
}