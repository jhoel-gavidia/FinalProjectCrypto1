package com.example.FinalProjectCrypto1.model.procesos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipo_operacion")
public class TipoOperacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_tipo_operacion")
    private Integer codTipoOperacion;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 30)
    @Column(nullable = false, unique = true, length = 30)
    private String descripcion;

}