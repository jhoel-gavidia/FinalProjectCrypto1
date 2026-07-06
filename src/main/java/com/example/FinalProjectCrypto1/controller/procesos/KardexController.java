package com.example.FinalProjectCrypto1.controller.procesos;

import com.example.FinalProjectCrypto1.model.procesos.Kardex;
import com.example.FinalProjectCrypto1.service.procesos.KardexService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kardex")
@RequiredArgsConstructor
public class KardexController {

    private final KardexService kardexService;

    @PreAuthorize("@permisoService.tienePermiso('Kardex', 'VER')")
    @GetMapping
    public ResponseEntity<List<Kardex>> listar() {
        return ResponseEntity.ok(kardexService.listar());
    }

    @PreAuthorize("@permisoService.tienePermiso('Kardex', 'VER')")
    @GetMapping("/producto/{codProducto}")
    public ResponseEntity<List<Kardex>> listarPorProducto(@PathVariable Integer codProducto) {
        return ResponseEntity.ok(kardexService.listarPorProducto(codProducto));
    }
}