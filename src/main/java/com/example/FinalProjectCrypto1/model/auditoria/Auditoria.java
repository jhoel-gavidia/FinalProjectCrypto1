package com.example.FinalProjectCrypto1.model.auditoria;

import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_auditoria")
    private Integer codAuditoria;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(nullable = false,length = 50)
    private String modulo;

    @Column(nullable = false,length = 50)
    private String tablaAfectada;

    @Column(nullable = false,length = 20)
    private String operacion;

    private Integer codigoRegistro;

    @Lob
    private String valorAnterior;

    @Lob
    private String valorNuevo;

    private LocalDateTime fechaHora;

    @Column(length = 45)
    private String ipOrigen;

    @Column(length = 100)
    private String equipo;

    @Column(length = 150)
    private String navegador;

}
