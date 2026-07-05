package com.example.FinalProjectCrypto1.model.ventas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "correlativo")
public class Correlativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_correlativo")
    private Integer codCorrelativo;

    @Column(nullable = false, unique = true, length = 10)
    private String serie;

    @Column(nullable = false)
    private Integer numeroActual;

}
