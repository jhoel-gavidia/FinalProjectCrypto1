package com.example.FinalProjectCrypto1.controller.seguridad;

import com.example.FinalProjectCrypto1.model.seguridad.Rol;
import com.example.FinalProjectCrypto1.service.seguridad.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    public ResponseEntity<List<Rol>> listar() {
        return ResponseEntity.ok(rolService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(rolService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Rol> guardar(@Valid @RequestBody Rol rol) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolService.guardar(rol));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizar(@PathVariable Integer id, @Valid @RequestBody Rol rol) {
        return ResponseEntity.ok(rolService.actualizar(id, rol));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Rol> eliminar(@PathVariable Integer id) {
        return ResponseEntity.ok(rolService.eliminar(id));
    }
}