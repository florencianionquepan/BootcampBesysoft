package com.besysoft.ejercitacion.repositorios.database;

import com.besysoft.ejercitacion.dominio.Genero;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GeneroRepository extends CrudRepository<Genero, Integer> {
    @Query("select g from Genero g where g.nombre=?1")
    Optional<Genero> findByName( String nombre);


}
