package com.example.FinalProjectCrypto1.controller.seguridad;

import com.example.FinalProjectCrypto1.dto.seguridad.AsignarPermisosRequest;
import com.example.FinalProjectCrypto1.model.seguridad.RolFuncionalidad;
import com.example.FinalProjectCrypto1.service.seguridad.RolFuncionalidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permisos")
@RequiredArgsConstructor
public class RolFuncionalidadController {

    private final RolFuncionalidadService rolFuncionalidadService;

    @PreAuthorize("@permisoService.tienePermiso('Permisos', 'VER')")
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<List<RolFuncionalidad>> listarPorRol(@PathVariable Integer idRol) {
        return ResponseEntity.ok(rolFuncionalidadService.listarPorRol(idRol));
    }

    @PreAuthorize("@permisoService.tienePermiso('Permisos', 'EDITAR')")
    @PutMapping("/rol/{idRol}")
    public ResponseEntity<List<RolFuncionalidad>> asignarPermisos(
            @PathVariable Integer idRol,
            @Valid @RequestBody AsignarPermisosRequest request) {

        return ResponseEntity.ok(rolFuncionalidadService.asignarPermisos(idRol, request));
    }
}