package com.Reservas.Reservas.Config;

import com.Reservas.Reservas.Reserva.Reserva;
import com.Reservas.Reservas.ReservaRepository.ReservaRepository;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final ReservaRepository reservaRepository;

    @Override
    public void run(String... args){
        if (reservaRepository.count() > 0){
            log.info("Datos cargados");
            return;
        }
        log.info("No hay datos guardados");

        reservaRepository.save(
                new Reserva(null, null, null, "12/07/2026", "Confirmada")
        );

        reservaRepository.save(
                new Reserva(null, null, null, "23/06/2026","Rechazada")
        );
    }
}
