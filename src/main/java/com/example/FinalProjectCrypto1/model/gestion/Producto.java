package com.example.FinalProjectCrypto1.model.gestion;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_producto")
    private Integer codProducto;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 80, message = "Máximo 80 caracteres")
    @Column(name = "nombre_producto", nullable = false, unique = true, length = 80)
    private String nombreProducto;

    @NotNull(message = "La categoría es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_categoria", nullable = false)
    private Categoria categoria;

    @NotNull(message = "El precio unitario es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @NotNull(message = "El precio por fracción es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioFraccion;

    @NotNull(message = "La cantidad por unidad es obligatoria")
    @PositiveOrZero(message = "La cantidad no puede ser negativa")
    @Column(nullable = false)
    private Integer cantidadUnidad;

    @NotNull(message = "La cantidad por fracción es obligatoria")
    @PositiveOrZero(message = "La cantidad no puede ser negativa")
    @Column(nullable = false)
    private Integer cantidadFraccion;

    @Column(nullable = false)
    private Boolean estado;

}
