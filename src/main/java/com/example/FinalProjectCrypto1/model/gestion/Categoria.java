package com.example.FinalProjectCrypto1.model.gestion;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_categoria")
    private Integer codCategoria;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 60, message = "Máximo 60 caracteres")
    @Column(name = "nombre_categoria", nullable = false, unique = true, length = 60)
    private String nombreCategoria;

    @Column(nullable = false)
    private Boolean estado;
}