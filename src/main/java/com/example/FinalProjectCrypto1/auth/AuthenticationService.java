package com.example.FinalProjectCrypto1.auth;

import com.example.FinalProjectCrypto1.dto.seguridad.CambiarPasswordDto;
import com.example.FinalProjectCrypto1.dto.seguridad.JwtDto;
import com.example.FinalProjectCrypto1.dto.seguridad.LoginDto;
import com.example.FinalProjectCrypto1.dto.seguridad.RegisterRequest;
import com.example.FinalProjectCrypto1.model.seguridad.Rol;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.seguridad.RolRepository;
import com.example.FinalProjectCrypto1.repository.seguridad.UsuarioRespository;
import com.example.FinalProjectCrypto1.security.JwtService;
import com.example.FinalProjectCrypto1.service.auditoria.AuditoriaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRespository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditoriaService auditoriaService;
    private final HttpServletRequest httpRequest;

    public JwtDto login(LoginDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsuario(),
                        request.getPassword()
                )
        );
        Usuario usuario = usuarioRepository.findByUsuario(request.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generateToken(usuario);

        auditoriaService.registrar(
                usuario.getIdUsuario(),
                "Seguridad",
                "usuario",
                "LOGIN",
                usuario.getIdUsuario(),
                null,
                null,
                httpRequest
        );

        return new JwtDto(token);
    }

    public JwtDto register(RegisterRequest request) {

        Rol rol = rolRepository.findById(request.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        if (usuarioRepository.findByUsuario(request.getUsuario()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }
        Usuario usuario = new Usuario();
        usuario.setUsuario(request.getUsuario());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rol);
        usuario.setEstado(true);

        Usuario usuarioGuardador = usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuarioGuardador);

        auditoriaService.registrar(
                usuarioGuardador.getIdUsuario(),
                "Seguridad",
                "usuario",
                "INSERT",
                usuarioGuardador.getIdUsuario(),
                null,
                usuarioGuardador.getUsuario(),
                httpRequest
        );

        return new JwtDto(token);
    }

    public void cambiarPassword(CambiarPasswordDto request) {

        Usuario usuario = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (!passwordEncoder.matches(request.getPasswordActual(), usuario.getPassword())) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }

        usuario.setPassword(passwordEncoder.encode(request.getPasswordNueva()));

        usuarioRepository.save(usuario);

        auditoriaService.registrar(
                usuario.getIdUsuario(),
                "Seguridad",
                "usuario",
                "UPDATE",
                usuario.getIdUsuario(),
                "password anterior (oculta)",
                "password actualizada",
                httpRequest
        );
    }
}
