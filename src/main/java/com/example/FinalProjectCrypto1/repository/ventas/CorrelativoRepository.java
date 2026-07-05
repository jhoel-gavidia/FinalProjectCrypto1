package com.example.FinalProjectCrypto1.repository.ventas;

import com.example.FinalProjectCrypto1.model.ventas.Correlativo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorrelativoRepository extends JpaRepository<Correlativo,Integer> {

    Optional<Correlativo> findBySerie(String serie);

}
