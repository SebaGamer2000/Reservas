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
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long idUsuario;

    @Column
    private Long idClase;

    @Column(nullable = false)
    private String fecha;

    @Column (nullable = false)
    private String estado;
}
