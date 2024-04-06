package com.besysoft.ejercitacion.dto;

import com.besysoft.ejercitacion.dominio.Personaje;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PeliculaRespDTO {
    private int id;
    private String titulo;
    private LocalDate fechaCreacion;
    private int calificacion;
    private String imagenURL;
    @JsonIgnoreProperties(value="peliculas")
    private List<PersonajeRespDTO> personajes;

}
