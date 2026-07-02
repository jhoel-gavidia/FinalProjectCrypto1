package com.example.FinalProjectCrypto1.auth;

import com.example.FinalProjectCrypto1.dto.seguridad.JwtDto;
import com.example.FinalProjectCrypto1.dto.seguridad.LoginDto;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.seguridad.UsuarioRespository;
import com.example.FinalProjectCrypto1.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UsuarioRespository usuarioRespository;

    public JwtDto login(LoginDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsuario(),
                        request.getPassword()
                )
        );
        Usuario usuario = usuarioRespository.findByUsuario(request.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generateToken(usuario);

        return new JwtDto(token);
    }


}
