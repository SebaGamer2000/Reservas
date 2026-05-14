package com.Reservas.Reservas.ReservaRepository;

import com.Reservas.Reservas.Reserva.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
