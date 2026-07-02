package com.example.FinalProjectCrypto1.model.seguridad;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "rol")
@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;


    @Column(name = "nombre_rol", unique = true, length = 40)
    private String nombreRol;

    private Boolean estado;
}
