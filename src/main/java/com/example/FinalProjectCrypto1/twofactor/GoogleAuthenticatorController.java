package com.example.FinalProjectCrypto1.twofactor;

import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.seguridad.UsuarioRespository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/2fa")
@RequiredArgsConstructor
public class GoogleAuthenticatorController {

    private final GoogleAuthenticatorService googleAuthenticatorService;
    private final UsuarioRespository usuarioRespository;

    private Usuario usuarioAutenticado() {
        return (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @PostMapping("/setup")
    public ResponseEntity<GoogleAuthenticatorResponse> setup() {

        Usuario usuario = usuarioRespository.findById(usuarioAutenticado().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String secret = googleAuthenticatorService.generarSecret();

        usuario.setSecretKey(secret);
        usuario.setTwoFactorEnabled(true);

        usuarioRespository.save(usuario);

        String otpAuthUrl =
                "otpauth://totp/FinalProjectCrypto:"
                        + usuario.getUsuario()
                        + "?secret="
                        + secret
                        + "&issuer=FinalProjectCrypto";

        String qr = QRCodeGenerator.generarQR(otpAuthUrl);

        return ResponseEntity.ok(
                new GoogleAuthenticatorResponse(secret, qr)
        );

    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@Valid @RequestBody VerifyCodeRequest request) {

        Usuario usuario = usuarioRespository.findById(usuarioAutenticado().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getSecretKey() == null) {
            return ResponseEntity.badRequest()
                    .body("El usuario no tiene 2FA configurado");
        }

        boolean valido = googleAuthenticatorService.verificarCodigo(
                usuario.getSecretKey(),
                request.getCodigo()
        );

        if (!valido) {
            return ResponseEntity.badRequest()
                    .body("Código inválido");
        }

        return ResponseEntity.ok("Código válido");

    }

}