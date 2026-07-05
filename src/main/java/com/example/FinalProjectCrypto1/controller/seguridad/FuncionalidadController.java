package com.example.FinalProjectCrypto1.controller.seguridad;

import com.example.FinalProjectCrypto1.dto.seguridad.FuncionalidadNodoDto;
import com.example.FinalProjectCrypto1.model.seguridad.Funcionalidad;
import com.example.FinalProjectCrypto1.service.seguridad.FuncionalidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionalidades")
@RequiredArgsConstructor
public class FuncionalidadController {

    private final FuncionalidadService funcionalidadService;

    @GetMapping
    public ResponseEntity<List<Funcionalidad>> listar() {
        return ResponseEntity.ok(funcionalidadService.listar());
    }

    @GetMapping("/arbol")
    public ResponseEntity<List<FuncionalidadNodoDto>> obtenerArbol() {
        return ResponseEntity.ok(funcionalidadService.obtenerArbol());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionalidad> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(funcionalidadService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Funcionalidad> guardar(@Valid @RequestBody Funcionalidad funcionalidad) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(funcionalidadService.guardar(funcionalidad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionalidad> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Funcionalidad funcionalidad) {

        return ResponseEntity.ok(funcionalidadService.actualizar(id, funcionalidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        funcionalidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}