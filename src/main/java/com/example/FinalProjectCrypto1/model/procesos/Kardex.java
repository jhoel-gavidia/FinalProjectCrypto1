package com.example.FinalProjectCrypto1.model.procesos;

import com.example.FinalProjectCrypto1.model.gestion.Producto;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="kardex")
public class Kardex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cod_kardex")
    private Integer codKardex;

    @ManyToOne
    @JoinColumn(name="cod_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name="cod_tipo_operacion")
    private TipoOperacion tipoOperacion;

    private Integer cantidadInicial;

    private Integer cantidadMovimiento;

    private Integer cantidadFinal;

    private Integer saldoUnitario;

    private Integer saldoFraccionario;

    private LocalDateTime fechaHora;

    @Column(length = 20)
    private String codDocumento;

    @Column(length = 200)
    private String observacion;

    private Boolean estado;

    private LocalDateTime fechaRegistro;

    @ManyToOne
    @JoinColumn(name="cod_usuario")
    private Usuario usuario;

}
