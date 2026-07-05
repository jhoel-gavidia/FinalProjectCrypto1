package com.example.FinalProjectCrypto1.controller.seguridad;

import com.example.FinalProjectCrypto1.dto.seguridad.CambiarEstadoRequest;
import com.example.FinalProjectCrypto1.dto.seguridad.CambiarRolRequest;
import com.example.FinalProjectCrypto1.dto.seguridad.UsuarioDto;
import com.example.FinalProjectCrypto1.service.seguridad.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}/rol")
    public ResponseEntity<UsuarioDto> cambiarRol(
            @PathVariable Integer id,
            @Valid @RequestBody CambiarRolRequest request) {

        return ResponseEntity.ok(usuarioService.cambiarRol(id, request.getIdRol()));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<UsuarioDto> cambiarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody CambiarEstadoRequest request) {

        return ResponseEntity.ok(usuarioService.cambiarEstado(id, request.getEstado()));
    }
}