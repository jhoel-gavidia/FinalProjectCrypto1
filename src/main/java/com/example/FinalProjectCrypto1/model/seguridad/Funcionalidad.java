package com.example.FinalProjectCrypto1.model.seguridad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "funcionalidad")
@Entity
public class Funcionalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionalidad")
    private Integer idFuncionalidad;

    @Column(unique = true, length = 80)
    private String nombre;

    @Column(length = 60)
    private String icono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "padre")
    private Funcionalidad padre;

    @JsonIgnore
    @OneToMany(mappedBy = "padre")
    private List<Funcionalidad> hijos;
}
