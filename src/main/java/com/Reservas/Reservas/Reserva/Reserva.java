package com.Reservas.Reservas.Reserva;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Reservas")
public class Reserva {
    @Id
    @GeneratedValue
    private Long id;

    @Id
    @GeneratedValue
    private Long idUsuario;

    @Id
    @GeneratedValue
    private Long idSesion;

    @Column(nullable = false)
    private String fecha;

    @Column (nullable = false)
    private String estado;
}
