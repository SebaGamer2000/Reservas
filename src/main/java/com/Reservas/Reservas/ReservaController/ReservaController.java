package com.Reservas.Reservas.ReservaController;

import com.Reservas.Reservas.ReservaDTO.ReservaRequestDTO;
import com.Reservas.Reservas.ReservaDTO.ReservaResponseDTO;
import com.Reservas.Reservas.ReservaService.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Reservas")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> findAll(){
        return ResponseEntity.ok(reservaService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservaResponseDTO> findById(@PathVariable Long id){
        return reservaService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(
            @Valid @RequestBody ReservaRequestDTO dto
            ){
        return ResponseEntity.status(201).body(reservaService.guardar(dto));
    }
    @PutMapping("{id}")
    public ResponseEntity<ReservaResponseDTO> actualizar(
            @PathVariable Long id, @Valid @RequestBody ReservaRequestDTO dto
    ){
        return reservaService.actualizar(id,dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        if (reservaService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
