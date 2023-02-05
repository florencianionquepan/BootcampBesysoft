package com.besysoft.ejercitacion.repositorios.database;

import com.besysoft.ejercitacion.dominio.Pelicula;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PeliculaRepository extends CrudRepository<Pelicula,Integer> {

    @Query("select pel from Pelicula pel where pel.titulo=?1")
    Optional<Pelicula> findByTitle(String titulo);

    @Query("select pel from Pelicula pel where pel.fechaCreacion>=?1 and pel.fechaCreacion<=?2")
    Iterable<Pelicula> findBetweenDates(LocalDate desde,LocalDate hasta);

    @Query("select pel from Pelicula pel where pel.calificacion>=?1 and pel.calificacion<=?2")
    Iterable<Pelicula> findBetweenCalif(int desde, int hasta);

}
