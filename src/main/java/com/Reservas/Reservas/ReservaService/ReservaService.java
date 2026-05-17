package com.Reservas.Reservas.ReservaService;

import com.Reservas.Reservas.Reserva.Reserva;
import com.Reservas.Reservas.ReservaDTO.ClaseDTO;
import com.Reservas.Reservas.ReservaDTO.ReservaRequestDTO;
import com.Reservas.Reservas.ReservaDTO.ReservaResponseDTO;
import com.Reservas.Reservas.ReservaDTO.UsuarioDTO;
import com.Reservas.Reservas.ReservaRepository.ReservaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final ReservaRepository reservaRepository;


    @Autowired
    private WebClient.Builder webClientBuilder;

    private ReservaResponseDTO maptoDTO(Reserva reserva){
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getIdUsuario(),
                reserva.getIdClase(),
                reserva.getFecha(),
                reserva.getEstado()
        );
    }
    public List<ReservaResponseDTO> findAll(){
        return reservaRepository.findAll().stream().map(this::maptoDTO).collect(Collectors.toList());
    }

    public Optional<ReservaResponseDTO> findById(Long id) {return reservaRepository.findById(id).map(this::maptoDTO);}

    public ReservaResponseDTO guardar(ReservaRequestDTO dto){
        UsuarioDTO usuarioDTO = webClientBuilder.build()
                .get()
                .uri("http://USUARIO/gym/socios/" + dto.getIdUsuario())
                .retrieve()
                .bodyToMono(UsuarioDTO.class)
                .block();

        ClaseDTO claseDTO = webClientBuilder.build()
                .get()
                .uri("http://CLASES/api/clases/" + dto.getIdClase())
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response ->
                        Mono.error(new RuntimeException("La clase con id " + dto.getIdClase() + " no existe."))
                )
                .bodyToMono(ClaseDTO.class)
                .block();

        if (claseDTO.getCupos() <= 0) {
            throw new RuntimeException("La clase no tiene cupos disponibles.");
        }

        if (usuarioDTO == null) {
            throw new RuntimeException("No se puede confirmar la reserva: El socio no existe o está Inactivo.");
        }

        Reserva reserva = new Reserva(
                null,
                dto.getIdUsuario(),
                dto.getIdClase(),
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
