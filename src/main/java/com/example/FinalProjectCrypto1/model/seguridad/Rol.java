package com.example.FinalProjectCrypto1.model.seguridad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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


    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 40, message = "Máximo 40 caracteres")
    @Column(name = "nombre_rol", unique = true, length = 40)
    private String nombreRol;

    private Boolean estado;
}
