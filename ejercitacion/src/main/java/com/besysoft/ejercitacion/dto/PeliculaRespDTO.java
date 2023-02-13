package com.besysoft.ejercitacion.dto;

import com.besysoft.ejercitacion.dominio.Personaje;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaRespDTO {
    private int id;
    private String titulo;
    private LocalDate fechaCreacion;
    private int calificacion;
    private List<Personaje> personajes;

}
