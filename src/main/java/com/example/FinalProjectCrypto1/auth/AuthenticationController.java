package com.example.FinalProjectCrypto1.auth;

import com.example.FinalProjectCrypto1.dto.seguridad.CambiarPasswordDto;
import com.example.FinalProjectCrypto1.dto.seguridad.JwtDto;
import com.example.FinalProjectCrypto1.dto.seguridad.LoginDto;
import com.example.FinalProjectCrypto1.dto.seguridad.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginDto request) {

        JwtDto response = authenticationService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtDto> register(@Valid @RequestBody RegisterRequest request) {
        JwtDto response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/cambiar-password")
    public ResponseEntity<String> cambiarPassword(@Valid @RequestBody CambiarPasswordDto request) {
        authenticationService.cambiarPassword(request);
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }
}
