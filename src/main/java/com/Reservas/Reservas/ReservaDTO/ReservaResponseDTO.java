package com.Reservas.Reservas.ReservaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaResponseDTO extends RepresentationModel<ReservaResponseDTO> {
    private Long id;
    private Long idUsuario;
    private Long idClase;
    private String fecha;
    private String estado;
}
