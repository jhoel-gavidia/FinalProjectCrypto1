package com.example.FinalProjectCrypto1.twofactor;

import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.seguridad.UsuarioRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/2fa")
@RequiredArgsConstructor
public class GoogleAuthenticatorController {

    private final GoogleAuthenticatorService googleAuthenticatorService;
    private final UsuarioRespository usuarioRespository;

    @PostMapping("/setup/{idUsuario}")
    public ResponseEntity<GoogleAuthenticatorResponse> setup(
            @PathVariable Integer idUsuario) {

        Usuario usuario = usuarioRespository.findById(idUsuario)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

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
    public ResponseEntity<String> verify(
            @RequestBody VerifyCodeRequest request) {

        Usuario usuario = usuarioRespository.findById(request.getIdUsuario())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

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