package com.besysoft.ejercitacion.dto;

import com.besysoft.ejercitacion.dominio.Pelicula;
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
public class GeneroReqDTO {
    private int id;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]+$", message = "solamente permite caracteres de la A - Z")
    @Size(min = 3, max = 20)
    private String nombre;
    private List<PeliculaRespDTO> peliculas;
}
