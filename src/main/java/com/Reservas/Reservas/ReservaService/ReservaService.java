package com.Reservas.Reservas.ReservaService;

import com.Reservas.Reservas.Reserva.Reserva;
import com.Reservas.Reservas.ReservaDTO.ClaseDTO;
import com.Reservas.Reservas.ReservaDTO.ReservaRequestDTO;
import com.Reservas.Reservas.ReservaDTO.ReservaResponseDTO;
import com.Reservas.Reservas.ReservaDTO.UsuarioDTO;
import com.Reservas.Reservas.ReservaRepository.ReservaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);


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
    //Listar todas las reservas
    public List<ReservaResponseDTO> findAll(){
        return reservaRepository.findAll().stream().map(this::maptoDTO).collect(Collectors.toList());
    }
    //Encontrar reserva por ID
    public Optional<ReservaResponseDTO> findById(Long id) {return reservaRepository.findById(id).map(this::maptoDTO);}
    //Guarda una reserva usando idUsuario del microservicio usuarios e idClases del microservicio clases
    public ReservaResponseDTO guardar(ReservaRequestDTO dto){
        log.info("Guardando reserva...");
        UsuarioDTO usuarioDTO = webClientBuilder.build()
                .get()
                .uri("http://USUARIO/gym/socios/" + dto.getIdUsuario())
                .retrieve()
                .bodyToMono(UsuarioDTO.class)
                .block();

        ClaseDTO claseDTO = webClientBuilder.build()
                .get()
                .uri("https://clases-a178.onrender.com/api/clases/" + dto.getIdClase())
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response ->
                        Mono.error(new RuntimeException("La clase con id " + dto.getIdClase() + " no existe."))
                )
                .bodyToMono(ClaseDTO.class)
                .block();

        if (claseDTO.getCupos() <= 0) {
            log.warn("Esta clase no tiene cupos disponibles");
            throw new RuntimeException("La clase no tiene cupos disponibles.");
        }

        if (usuarioDTO == null) {
            log.warn("No se puede guardar la reserva porque este socio no existe");
            throw new RuntimeException("No se puede confirmar la reserva: El socio no existe o está Inactivo.");
        }

        Reserva reserva = new Reserva(
                null,
                dto.getIdUsuario(),
                dto.getIdClase(),
                dto.getFecha(),
                dto.getEstado()
        );
        log.info("Reserva guardada");
        return maptoDTO(reservaRepository.save(reserva));
    }
    //Actualiza una reserva
    public Optional<ReservaResponseDTO> actualizar(Long id, ReservaRequestDTO dto){
        return reservaRepository.findById(id).map(existente->{
            existente.setFecha(dto.getFecha());
            existente.setEstado(dto.getEstado());
            log.info("Reserva actualizada correctamente");
            return maptoDTO(reservaRepository.save(existente));
        });
    }
    //Elimina una reserva
    public void eliminar(Long id){reservaRepository.deleteById(id);}{
        log.info("Reserva eliminada correctamente");
    }
}
