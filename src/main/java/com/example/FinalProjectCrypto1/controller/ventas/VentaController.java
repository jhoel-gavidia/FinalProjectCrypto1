package com.example.FinalProjectCrypto1.controller.ventas;

import com.example.FinalProjectCrypto1.dto.ventas.VentaRequest;
import com.example.FinalProjectCrypto1.service.ventas.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<String> registrarVenta(
            @Valid @RequestBody VentaRequest request) {

        ventaService.registrarVenta(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Venta registrada correctamente");
    }

}
