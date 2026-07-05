package com.example.FinalProjectCrypto1.model.seguridad;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
@Entity
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(unique = true, length = 30)
    private String usuario;

    @Column(length = 255)
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

    @Column(nullable = false)
    private Boolean estado = true;

    @Column(name = "fech_registro")
    @CreationTimestamp
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_creacion")
    private Usuario usuarioCreacion;

    @Column(name = "fech_modificacion")
    @UpdateTimestamp
    private LocalDateTime fechaModificacion;

    @Column(name = "secret_key", length = 100)
    private String secretKey;

    @Column(name = "two_factor_enabled", nullable = false)
    private Boolean twoFactorEnabled = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+ rol.getNombreRol()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return estado;
    }

    @Override
    public boolean isAccountNonLocked() {
        return estado;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return estado;
    }

    @Override
    public boolean isEnabled() {
        return estado;
    }
}
