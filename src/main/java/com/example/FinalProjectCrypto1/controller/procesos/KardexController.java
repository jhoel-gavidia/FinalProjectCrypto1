package com.example.FinalProjectCrypto1.controller.procesos;

import com.example.FinalProjectCrypto1.dto.procesos.KardexResponseDto;
import com.example.FinalProjectCrypto1.model.procesos.Kardex;
import com.example.FinalProjectCrypto1.service.procesos.KardexService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kardex")
@RequiredArgsConstructor
public class KardexController {

    private final KardexService kardexService;

    private KardexResponseDto toDto(Kardex kardex) {
        return new KardexResponseDto(
                kardex.getCodKardex(),
                kardex.getProducto().getCodProducto(),
                kardex.getProducto().getNombreProducto(),
                kardex.getTipoOperacion().getDescripcion(),
                kardex.getCantidadInicial(),
                kardex.getCantidadMovimiento(),
                kardex.getCantidadFinal(),
                kardex.getSaldoUnitario(),
                kardex.getSaldoFraccionario(),
                kardex.getFechaHora(),
                kardex.getCodDocumento(),
                kardex.getObservacion(),
                kardex.getUsuario() != null ? kardex.getUsuario().getUsuario() : null
        );
    }

    @PreAuthorize("@permisoService.tienePermiso('Kardex', 'VER')")
    @GetMapping
    public ResponseEntity<List<KardexResponseDto>> listar() {
        List<KardexResponseDto> kardex = kardexService.listar().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(kardex);
    }

    @PreAuthorize("@permisoService.tienePermiso('Kardex', 'VER')")
    @GetMapping("/producto/{codProducto}")
    public ResponseEntity<List<KardexResponseDto>> listarPorProducto(@PathVariable Integer codProducto) {
        List<KardexResponseDto> kardex = kardexService.listarPorProducto(codProducto).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(kardex);
    }
}