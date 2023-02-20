package com.besysoft.ejercitacion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonajeReqDTO {
    private int id;
    private String nombre;
    private int edad;
    private double peso;
    private String historia;
    @JsonIgnoreProperties(value="personajes")
    private List<PeliculaRespDTO> peliculas;

}
