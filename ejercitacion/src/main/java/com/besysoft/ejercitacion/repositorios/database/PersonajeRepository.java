package com.besysoft.ejercitacion.repositorios.database;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonajeRepository extends CrudRepository<Personaje,Integer> {
    @Query("select per from Personaje per where per.nombre=?1")
    List<Personaje> findByName(String nombre);
    @Query("select per from Personaje per where per.edad=?1")
    List<Personaje> findByAge(int edad);
    @Query("select per from Personaje per where per.edad>=?1 and per.edad<=?2")
    List<Personaje> findBetweenAges(int desde, int hasta);
}
