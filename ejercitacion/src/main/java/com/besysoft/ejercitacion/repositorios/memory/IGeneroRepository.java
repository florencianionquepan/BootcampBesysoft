package com.besysoft.ejercitacion.repositorios.memory;

import com.besysoft.ejercitacion.dominio.Genero;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface IGeneroRepository {
    List<Genero> verGeneros();
    Genero altaGen(Genero gen);
    Genero modiGen(Genero gen,int id);
    Optional<Genero> buscarGeneroById(int id);
    Optional<Genero> buscarGeneroByNombre(String nombre);

}
