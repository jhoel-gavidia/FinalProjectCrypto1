package com.example.FinalProjectCrypto1.controller.gestion;

import com.example.FinalProjectCrypto1.model.gestion.Cliente;
import com.example.FinalProjectCrypto1.service.gestion.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PreAuthorize("@permisoService.tienePermiso('Clientes', 'VER')")
    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(clienteService.listar());
    }

    @PreAuthorize("@permisoService.tienePermiso('Clientes', 'VER')")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @PreAuthorize("@permisoService.tienePermiso('Clientes', 'CREAR')")
    @PostMapping
    public ResponseEntity<Cliente> guardar(@Valid @RequestBody Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.guardar(cliente));
    }

    @PreAuthorize("@permisoService.tienePermiso('Clientes', 'EDITAR')")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Cliente cliente) {

        return ResponseEntity.ok(clienteService.actualizar(id, cliente));
    }

    @PreAuthorize("@permisoService.tienePermiso('Clientes', 'ELIMINAR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> eliminar(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.eliminar(id));
    }
}