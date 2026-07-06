package com.example.FinalProjectCrypto1.service.ventas;

import com.example.FinalProjectCrypto1.dto.ventas.ExtornoRequest;
import com.example.FinalProjectCrypto1.dto.ventas.VentaRequest;
import com.example.FinalProjectCrypto1.model.ventas.Venta;

import java.util.List;

public interface VentaService {

    void registrarVenta(VentaRequest request);

    void extornarVenta(ExtornoRequest request);

    List<Venta> listar();

    Venta buscarPorId(Integer id);
}
