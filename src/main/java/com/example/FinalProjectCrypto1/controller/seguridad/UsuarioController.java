package com.example.FinalProjectCrypto1.controller.seguridad;

import com.example.FinalProjectCrypto1.dto.seguridad.CambiarEstadoRequest;
import com.example.FinalProjectCrypto1.dto.seguridad.CambiarRolRequest;
import com.example.FinalProjectCrypto1.dto.seguridad.UsuarioDto;
import com.example.FinalProjectCrypto1.service.seguridad.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PreAuthorize("@permisoService.tienePermiso('Usuarios', 'VER')")
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @PreAuthorize("@permisoService.tienePermiso('Usuarios', 'VER')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PreAuthorize("@permisoService.tienePermiso('Usuarios', 'EDITAR')")
    @PutMapping("/{id}/rol")
    public ResponseEntity<UsuarioDto> cambiarRol(
            @PathVariable Integer id,
            @Valid @RequestBody CambiarRolRequest request) {

        return ResponseEntity.ok(usuarioService.cambiarRol(id, request.getIdRol()));
    }

    @PreAuthorize("@permisoService.tienePermiso('Usuarios', 'EDITAR')")
    @PutMapping("/{id}/estado")
    public ResponseEntity<UsuarioDto> cambiarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody CambiarEstadoRequest request) {

        return ResponseEntity.ok(usuarioService.cambiarEstado(id, request.getEstado()));
    }
}