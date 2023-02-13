package com.besysoft.ejercitacion.dto;

import com.besysoft.ejercitacion.dominio.Pelicula;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneroReqDTO {

    private int id;
    private String nombre;
    private List<Pelicula> listaPelis;
}
