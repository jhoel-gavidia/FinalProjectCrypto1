package com.example.FinalProjectCrypto1.service.gestion;

import com.example.FinalProjectCrypto1.exception.DuplicateResourceException;
import com.example.FinalProjectCrypto1.exception.ResourceNotFoundException;
import com.example.FinalProjectCrypto1.model.gestion.Cliente;
import com.example.FinalProjectCrypto1.model.gestion.TipoDocumento;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.gestion.ClienteRepository;
import com.example.FinalProjectCrypto1.repository.gestion.TipoDocumentoRepository;
import com.example.FinalProjectCrypto1.service.auditoria.AuditoriaService;
import com.example.FinalProjectCrypto1.util.DesUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final DesUtil desUtil;
    private final AuditoriaService auditoriaService;
    private final HttpServletRequest httpRequest;

    private Integer usuarioActualId() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return usuario.getIdUsuario();
    }

    @Override
    public List<Cliente> listar() {
        return clienteRepository.findByEstadoTrueOrderByCodClienteAsc();
    }

    @Override
    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findByCodClienteAndEstadoTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente no encontrado"));
    }

    @Override
    public Cliente guardar(Cliente cliente) {

        TipoDocumento tipoDocumento = tipoDocumentoRepository
                .findByCodTipoDocumentoAndEstadoTrue(
                        cliente.getTipoDocumento().getCodTipoDocumento())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tipo de documento no encontrado"));

        validarDatos(cliente, tipoDocumento);

        String documentoCifrado = desUtil.encrypt(cliente.getNumeroDocumento());

        Optional<Cliente> existente =
                clienteRepository.findByTipoDocumento_CodTipoDocumentoAndNumeroDocumento(
                        tipoDocumento.getCodTipoDocumento(),
                        documentoCifrado
                );

        if (existente.isPresent()) {
            throw new DuplicateResourceException("El cliente ya existe");
        }

        cliente.setTipoDocumento(tipoDocumento);
        cliente.setNumeroDocumento(documentoCifrado);
        cliente.setFechaNacimiento(
                desUtil.encrypt(cliente.getFechaNacimiento())
        );
        cliente.setEstado(true);

        Cliente clienteGuardado = clienteRepository.save(cliente);

        auditoriaService.registrar(
                usuarioActualId(),
                "Gestion",
                "cliente",
                "INSERT",
                clienteGuardado.getCodCliente(),
                null,
                clienteGuardado,
                httpRequest
        );

        return clienteGuardado;
    }

    @Override
    public Cliente actualizar(Integer id, Cliente cliente) {

        Cliente clienteBD = buscarPorId(id);

        Cliente snapshotAnterior = new Cliente(
                clienteBD.getCodCliente(),
                clienteBD.getTipoDocumento(),
                clienteBD.getNumeroDocumento(),
                clienteBD.getRazonSocial(),
                clienteBD.getNombreCliente(),
                clienteBD.getFechaNacimiento(),
                clienteBD.getEstado()
        );

        TipoDocumento tipoDocumento = tipoDocumentoRepository
                .findByCodTipoDocumentoAndEstadoTrue(
                        cliente.getTipoDocumento().getCodTipoDocumento())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tipo de documento no encontrado"));

        validarDatos(cliente, tipoDocumento);

        String documentoCifrado = desUtil.encrypt(cliente.getNumeroDocumento());

        Optional<Cliente> existente =
                clienteRepository.findByTipoDocumento_CodTipoDocumentoAndNumeroDocumento(
                        tipoDocumento.getCodTipoDocumento(),
                        documentoCifrado
                );

        if (existente.isPresent()
                && !existente.get().getCodCliente().equals(id)) {

            throw new DuplicateResourceException("El cliente ya existe");
        }

        clienteBD.setTipoDocumento(tipoDocumento);
        clienteBD.setNumeroDocumento(documentoCifrado);
        clienteBD.setFechaNacimiento(
                desUtil.encrypt(cliente.getFechaNacimiento())
        );
        clienteBD.setNombreCliente(cliente.getNombreCliente());
        clienteBD.setRazonSocial(cliente.getRazonSocial());

        Cliente clienteActualizado = clienteRepository.save(clienteBD);

        auditoriaService.registrar(
                usuarioActualId(),
                "Gestion",
                "cliente",
                "UPDATE",
                clienteActualizado.getCodCliente(),
                snapshotAnterior,
                clienteActualizado,
                httpRequest
        );

        return clienteActualizado;
    }

    @Override
    public Cliente eliminar(Integer id) {

        Cliente cliente = buscarPorId(id);

        cliente.setEstado(false);

        Cliente clienteEliminado = clienteRepository.save(cliente);

        auditoriaService.registrar(
                usuarioActualId(),
                "Gestion",
                "cliente",
                "DELETE",
                clienteEliminado.getCodCliente(),
                "estado: true",
                "estado: false",
                httpRequest
        );

        return clienteEliminado;
    }

    private void validarDatos(Cliente cliente, TipoDocumento tipoDocumento) {

        switch (tipoDocumento.getDescripcion().toUpperCase()) {

            case "DNI":

                if (cliente.getNumeroDocumento().length() != 8)
                    throw new RuntimeException("El DNI debe tener 8 dígitos");

                if (cliente.getNombreCliente() == null ||
                        cliente.getNombreCliente().isBlank())
                    throw new RuntimeException("Ingrese el nombre del cliente");

                break;

            case "RUC":

                if (cliente.getNumeroDocumento().length() != 11)
                    throw new RuntimeException("El RUC debe tener 11 dígitos");

                if (cliente.getRazonSocial() == null ||
                        cliente.getRazonSocial().isBlank())
                    throw new RuntimeException("Ingrese la razón social");

                break;
        }

    }

}
