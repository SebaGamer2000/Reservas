package com.Reservas.Reservas.ReservaDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequestDTO {
    @NotNull(message = "El id de usuario no puede ser nulo")
    private Long idUsuario;

    @NotNull(message = "El id de clase no puede ser nulo")
    private Long idClase;

    @NotBlank(message = "La fecha no puede estar vacia")
    private String fecha;

    @NotBlank(message = "El estado no puede estar vacio")
    private String estado;
}
