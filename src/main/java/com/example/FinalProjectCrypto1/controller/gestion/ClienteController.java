package com.example.FinalProjectCrypto1.controller.gestion;

import com.example.FinalProjectCrypto1.dto.gestion.ClienteResponseDto;
import com.example.FinalProjectCrypto1.model.gestion.Cliente;
import com.example.FinalProjectCrypto1.service.gestion.ClienteService;
import com.example.FinalProjectCrypto1.util.DesUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final DesUtil desUtil;

    private ClienteResponseDto toDto(Cliente cliente) {
        return new ClienteResponseDto(
                cliente.getCodCliente(),
                cliente.getTipoDocumento().getCodTipoDocumento(),
                cliente.getTipoDocumento().getDescripcion(),
                desUtil.decrypt(cliente.getNumeroDocumento()),
                cliente.getRazonSocial(),
                cliente.getNombreCliente(),
                desUtil.decrypt(cliente.getFechaNacimiento()),
                cliente.getEstado()
        );
    }

    @PreAuthorize("@permisoService.tienePermiso('Clientes', 'VER')")
    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> listar() {
        List<ClienteResponseDto> clientes = clienteService.listar().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientes);
    }

    @PreAuthorize("@permisoService.tienePermiso('Clientes', 'VER')")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(toDto(clienteService.buscarPorId(id)));
    }

    @PreAuthorize("@permisoService.tienePermiso('Clientes', 'CREAR')")
    @PostMapping
    public ResponseEntity<ClienteResponseDto> guardar(@Valid @RequestBody Cliente cliente) {
        Cliente guardado = clienteService.guardar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(guardado));
    }

    @PreAuthorize("@permisoService.tienePermiso('Clientes', 'EDITAR')")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Cliente cliente) {

        Cliente actualizado = clienteService.actualizar(id, cliente);
        return ResponseEntity.ok(toDto(actualizado));
    }

    @PreAuthorize("@permisoService.tienePermiso('Clientes', 'ELIMINAR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> eliminar(@PathVariable Integer id) {
        return ResponseEntity.ok(toDto(clienteService.eliminar(id)));
    }
}