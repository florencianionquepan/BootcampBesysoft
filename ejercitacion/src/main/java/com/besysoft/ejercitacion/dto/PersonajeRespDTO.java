package com.besysoft.ejercitacion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonajeRespDTO {
    private int id;
    private String nombre;
    private int edad;
    private double peso;
    private String historia;
    @JsonIgnoreProperties(value="personajes")
    private List<PeliculaRespDTO> peliculas;
}
