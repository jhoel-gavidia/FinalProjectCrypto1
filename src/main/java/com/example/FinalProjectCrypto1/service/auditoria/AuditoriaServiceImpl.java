package com.example.FinalProjectCrypto1.service.auditoria;

import com.example.FinalProjectCrypto1.model.auditoria.Auditoria;
import com.example.FinalProjectCrypto1.model.seguridad.Usuario;
import com.example.FinalProjectCrypto1.repository.auditoria.AuditoriaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditoriaServiceImpl implements AuditoriaService{

    private final AuditoriaRepository auditoriaRepository;

    private final ObjectMapper objectMapper;

    @Override
    public void registrar(
            Integer codUsuario,
            String modulo,
            String tabla,
            String operacion,
            Integer codigoRegistro,
            Object valorAnterior,
            Object valorNuevo,
            HttpServletRequest request) {

        try{

            Auditoria auditoria = new Auditoria();

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(codUsuario);

            auditoria.setUsuario(usuario);
            auditoria.setModulo(modulo);
            auditoria.setTablaAfectada(tabla);
            auditoria.setOperacion(operacion);
            auditoria.setCodigoRegistro(codigoRegistro);

            auditoria.setValorAnterior(
                    valorAnterior==null ?
                            null :
                            objectMapper.writeValueAsString(valorAnterior)
            );

            auditoria.setValorNuevo(
                    valorNuevo==null ?
                            null :
                            objectMapper.writeValueAsString(valorNuevo)
            );

            auditoria.setFechaHora(LocalDateTime.now());

            auditoria.setIpOrigen(
                    request.getRemoteAddr()
            );

            auditoria.setEquipo(
                    request.getRemoteHost()
            );

            auditoria.setNavegador(
                    request.getHeader("User-Agent")
            );

            auditoriaRepository.save(auditoria);

        }catch (Exception e){

            throw new RuntimeException(e);

        }

    }

}
