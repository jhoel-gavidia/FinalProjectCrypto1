package com.example.FinalProjectCrypto1.controller.procesos;

import com.example.FinalProjectCrypto1.dto.procesos.IngresoProductoDTO;
import com.example.FinalProjectCrypto1.service.procesos.IngresoProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingresos")
@RequiredArgsConstructor
public class IngresoProductoController {

    private final IngresoProductoService ingresoProductoService;

    @PostMapping
    public ResponseEntity<String> registrar(
            @Valid @RequestBody IngresoProductoDTO dto) {

        ingresoProductoService.registrarIngreso(dto);

        return ResponseEntity.ok("Ingreso registrado correctamente");
    }

}
