package com.besysoft.ejercitacion.servicios.interfaces;

import com.besysoft.ejercitacion.dominio.Genero;

import java.util.List;


public interface IGeneroService {
    List<Genero> verGeneros();
    Genero altaGenero(Genero genero);
    Genero modiGenero(Genero genero, int id);
}
