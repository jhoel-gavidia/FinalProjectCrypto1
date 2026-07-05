package com.example.FinalProjectCrypto1.model.gestion;

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
@Table(name = "tipo_documento")
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_tipo_documento")
    private Integer codTipoDocumento;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 30, message = "Máximo 30 caracteres")
    @Column(nullable = false, unique = true, length = 30)
    private String descripcion;

    @Column(nullable = false)
    private Boolean estado;

}
