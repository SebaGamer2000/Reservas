package com.Reservas.Reservas.ReservaService;

import com.Reservas.Reservas.Reserva.Reserva;
import com.Reservas.Reservas.ReservaDTO.ReservaRequestDTO;
import com.Reservas.Reservas.ReservaDTO.ReservaResponseDTO;
import com.Reservas.Reservas.ReservaRepository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final ReservaRepository reservaRepository;

    private ReservaResponseDTO maptoDTO(Reserva reserva){
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getIdUsuario(),
                reserva.getIdSesion(),
                reserva.getFecha(),
                reserva.getEstado()
        );
    }
    public List<ReservaResponseDTO> findAll(){
        return reservaRepository.findAll().stream().map(this::maptoDTO).collect(Collectors.toList());
    }

    public Optional<ReservaResponseDTO> findById(Long id) {return reservaRepository.findById(id).map(this::maptoDTO);}

    public ReservaResponseDTO guardar(ReservaRequestDTO dto){
        Reserva reserva = new Reserva(
                null,
                null,
                null,
                dto.getFecha(),
                dto.getEstado()
        );
        return maptoDTO(reservaRepository.save(reserva));
    }

    public Optional<ReservaResponseDTO> actualizar(Long id, ReservaRequestDTO dto){
        return reservaRepository.findById(id).map(existente->{
            existente.setFecha(dto.getFecha());
            existente.setEstado(dto.getEstado());
            return maptoDTO(reservaRepository.save(existente));
        });
    }

    public void eliminar(Long id){reservaRepository.deleteById(id);}
}
