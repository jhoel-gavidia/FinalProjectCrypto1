package com.example.FinalProjectCrypto1.service.gestion;

import com.example.FinalProjectCrypto1.model.gestion.Cliente;

import java.util.List;

public interface ClienteService {

    List<Cliente> listar();

    Cliente buscarPorId(Integer id);

    Cliente guardar(Cliente cliente);

    Cliente actualizar(Integer id, Cliente cliente);

    Cliente eliminar(Integer id);

}
