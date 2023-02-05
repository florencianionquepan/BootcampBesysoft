package com.besysoft.ejercitacion.repositorios.memory;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;

import java.util.List;
import java.util.Optional;

public interface IPersonajeRepository {
    List<Personaje> verPerso();
    List<Personaje> buscarPersoByNombre(String nombre);
    List<Personaje> buscarPersoByEdad(int edad);
    List<Personaje> buscarPersoRangoEdad(int desde, int hasta);
    Personaje altaPersonaje(Personaje perso);
    Optional<Personaje> buscarPersoById(int id);
    Personaje modiPersonaje(Personaje perso, int id);
    void addPeliAPerso(Pelicula peliNueva);
    void removePeliDePerson(Pelicula peliVieja);
}
