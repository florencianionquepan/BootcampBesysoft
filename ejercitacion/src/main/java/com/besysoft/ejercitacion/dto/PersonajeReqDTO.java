package com.besysoft.ejercitacion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonajeReqDTO {
    private int id;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]+$", message = "solamente permite caracteres de la A - Z")
    @Size(min = 3, max = 20)
    private String nombre;
    private int edad;
    private double peso;
    private String historia;
    @JsonIgnoreProperties(value="personajes")
    private List<PeliculaRespDTO> peliculas;

}
