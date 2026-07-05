package com.example.FinalProjectCrypto1.controller.procesos;

import com.example.FinalProjectCrypto1.dto.procesos.AjusteProductoDTO;
import com.example.FinalProjectCrypto1.service.procesos.AjusteProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ajustes")
@RequiredArgsConstructor
public class AjusteProductoController {

    private final AjusteProductoService ajusteProductoService;

    @PreAuthorize("@permisoService.tienePermiso('Ajuste de Productos', 'CREAR')")
    @PostMapping
    public ResponseEntity<String> registrar(
            @Valid @RequestBody AjusteProductoDTO dto) {

        ajusteProductoService.registrarAjuste(dto);

        return ResponseEntity.ok("Ajuste registrado correctamente");
    }

}