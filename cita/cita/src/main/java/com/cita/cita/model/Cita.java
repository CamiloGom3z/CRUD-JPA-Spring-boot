package com.cita.cita.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity

public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Paciente paciente;
    @ManyToOne
    private Medico medico;

    private String fecha;

    private String hora;

}
