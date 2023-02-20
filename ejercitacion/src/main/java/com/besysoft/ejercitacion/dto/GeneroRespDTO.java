package com.besysoft.ejercitacion.dto;

import com.besysoft.ejercitacion.dominio.Pelicula;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GeneroRespDTO {
    private int id;
    private String nombre;
    private List<PeliculaRespDTO> peliculas;
}
