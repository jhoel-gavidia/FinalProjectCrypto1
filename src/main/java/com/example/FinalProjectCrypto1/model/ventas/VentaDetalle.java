package com.example.FinalProjectCrypto1.model.ventas;

import com.example.FinalProjectCrypto1.model.gestion.Producto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "venta_detalle")
public class VentaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_detalle")
    private Integer codDetalle;

    @ManyToOne
    @JoinColumn(name = "cod_venta", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "cod_producto", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidadUnidad;

    @Column(nullable = false)
    private Integer cantidadFraccion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

}
