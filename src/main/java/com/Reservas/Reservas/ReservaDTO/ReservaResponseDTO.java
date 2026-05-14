package com.Reservas.Reservas.ReservaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaResponseDTO {
    private Long id;
    private Long idUsuario;
    private Long idSesion;
    private String fecha;
    private String estado;
}
