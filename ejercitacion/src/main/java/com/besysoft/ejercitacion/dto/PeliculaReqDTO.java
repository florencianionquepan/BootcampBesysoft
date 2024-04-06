package com.besysoft.ejercitacion.dto;

import com.besysoft.ejercitacion.dominio.Personaje;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PeliculaReqDTO {
    private int id;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "solamente permite caracteres de la A - Z")
    @Size(min = 3, max = 20)
    private String titulo;
    private LocalDate fechaCreacion;
    @Min(1)
    @Max(5)
    private int calificacion;
    private String imagenURL;
    @JsonIgnoreProperties(value="peliculas")
    private List<PersonajeRespDTO> personajes;

}
