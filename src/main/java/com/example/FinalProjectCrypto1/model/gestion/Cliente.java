package com.example.FinalProjectCrypto1.model.gestion;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "cliente",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "cod_tipo_documento",
                                "numero_documento"
                        }
                )
        }
)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_cliente")
    private Integer codCliente;

    @NotNull(message = "El tipo de documento es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(max = 20)
    @Column(name = "numero_documento", nullable = false)
    private String numeroDocumento;

    @Size(max = 150)
    @Column(name = "razon_social")
    private String razonSocial;

    @Size(max = 120)
    @Column(name = "nombre_cliente")
    private String nombreCliente;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(name = "fecha_nacimiento", nullable = false)
    private String fechaNacimiento;

    @Column(nullable = false)
    private Boolean estado;

}
