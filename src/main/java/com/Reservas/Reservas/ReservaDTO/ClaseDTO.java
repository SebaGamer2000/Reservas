package com.Reservas.Reservas.ReservaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaseDTO {
    private Long id;
    private String nombreClase;
    private String descripcion;
    private Integer cupos;
}
