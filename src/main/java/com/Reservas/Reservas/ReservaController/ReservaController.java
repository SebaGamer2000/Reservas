package com.Reservas.Reservas.ReservaController;

import com.Reservas.Reservas.ReservaDTO.ReservaRequestDTO;
import com.Reservas.Reservas.ReservaDTO.ReservaResponseDTO;
import com.Reservas.Reservas.ReservaService.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/Reservas")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;
    //GetMapping para listar reservas
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> findAll(){List<ReservaResponseDTO> lista = reservaService.findAll();
        lista.forEach(dto ->
                dto.add(linkTo(methodOn(ReservaController.class).findById(dto.getId())).withSelfRel())
        );return ResponseEntity.ok(reservaService.findAll());
    }
    //GetMapping para listar reservas por ID
    @GetMapping("{id}")
    public ResponseEntity<ReservaResponseDTO> findById(@PathVariable Long id){return reservaService.findById(id)
            .map(dto -> {
                dto.add(linkTo(methodOn(ReservaController.class).findById(id)).withSelfRel());
                dto.add(linkTo(methodOn(ReservaController.class).findAll()).withRel("todas"));
                return ResponseEntity.ok(dto);
            }).orElse(ResponseEntity.notFound().build());
    }
    //PostMapping para crear y guardar reservas
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(
            @Valid @RequestBody ReservaRequestDTO dto
            ){ReservaResponseDTO nueva = reservaService.guardar(dto);
        nueva.add(linkTo(methodOn(ReservaController.class).findById(nueva.getId())).withSelfRel());
        nueva.add(linkTo(methodOn(ReservaController.class).findAll()).withRel("todas"));
    return ResponseEntity.status(201).body(nueva);
    }
    //PutMapping para actualizar una reserva usando su ID
    @PutMapping("{id}")
    public ResponseEntity<ReservaResponseDTO> actualizar(
            @PathVariable Long id, @Valid @RequestBody ReservaRequestDTO dto
    ){
        return reservaService.actualizar(id,dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    //DeleteMapping para eliminar una reserva
    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        if (reservaService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
