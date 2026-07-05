package com.example.FinalProjectCrypto1.controller.gestion;

import com.example.FinalProjectCrypto1.model.gestion.TipoDocumento;
import com.example.FinalProjectCrypto1.service.gestion.TipoDocumentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-documento")
@RequiredArgsConstructor
public class TipoDocumentoController {

    private final TipoDocumentoService tipoDocumentoService;

    @PreAuthorize("@permisoService.tienePermiso('Roles', 'VER')")
    @GetMapping
    public ResponseEntity<List<TipoDocumento>> listar() {
        return ResponseEntity.ok(tipoDocumentoService.listar());
    }

    @PreAuthorize("@permisoService.tienePermiso('Roles', 'VER')")
    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumento> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(tipoDocumentoService.buscarPorId(id));
    }

    @PreAuthorize("@permisoService.tienePermiso('Roles', 'CREAR')")
    @PostMapping
    public ResponseEntity<TipoDocumento> guardar(@Valid @RequestBody TipoDocumento tipoDocumento) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tipoDocumentoService.guardar(tipoDocumento));
    }

    @PreAuthorize("@permisoService.tienePermiso('Roles', 'EDITAR')")
    @PutMapping("/{id}")
    public ResponseEntity<TipoDocumento> actualizar(@PathVariable Integer id,
                                                    @Valid @RequestBody TipoDocumento tipoDocumento) {
        return ResponseEntity.ok(tipoDocumentoService.actualizar(id, tipoDocumento));
    }

    @PreAuthorize("@permisoService.tienePermiso('Roles', 'ELIMINAR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<TipoDocumento> eliminar(@PathVariable Integer id) {
        return ResponseEntity.ok(tipoDocumentoService.eliminar(id));
    }

}
