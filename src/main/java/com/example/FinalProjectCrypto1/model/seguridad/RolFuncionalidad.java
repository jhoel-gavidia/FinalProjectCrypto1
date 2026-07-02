package com.example.FinalProjectCrypto1.model.seguridad;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "rol_funcionalidad")
@Entity
public class RolFuncionalidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol_funcionalidad")
    private Integer idRolFuncionalidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol")
    private Rol idRol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionalidad")
    private Funcionalidad idFuncionalidad;

    private Boolean ver;

    private Boolean crear;

    private Boolean editar;

    private Boolean eliminar;

    private Boolean imprimir;
}
