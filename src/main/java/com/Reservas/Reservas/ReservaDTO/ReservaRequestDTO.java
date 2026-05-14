package com.Reservas.Reservas.ReservaDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequestDTO {
    @NotBlank(message = "La fecha no puede estar vacia")
    private String fecha;

    @NotBlank(message = "El estado no puede estar vacio")
    private String estado;
}
