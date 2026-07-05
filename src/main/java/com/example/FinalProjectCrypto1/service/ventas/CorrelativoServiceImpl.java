package com.example.FinalProjectCrypto1.service.ventas;

import com.example.FinalProjectCrypto1.model.ventas.Correlativo;
import com.example.FinalProjectCrypto1.repository.ventas.CorrelativoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CorrelativoServiceImpl
        implements CorrelativoService {

    private final CorrelativoRepository correlativoRepository;

    @Override
    public String generarCorrelativo(String serie) {

        Correlativo correlativo = correlativoRepository
                .findBySerie(serie)
                .orElseThrow();

        correlativo.setNumeroActual(
                correlativo.getNumeroActual()+1
        );

        correlativoRepository.save(correlativo);

        return serie + "-"
                + String.format("%08d",
                correlativo.getNumeroActual());

    }

}
