package com.besysoft.ejercitacion.repositorios.memory;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPeliculaRepository {
    List<Pelicula> verPelis();
    List<Pelicula> buscarPeliByTitulo(String titulo);
    List<Pelicula> buscarPeliByGenero(String genero);
    List<Pelicula> buscarPeliByFechas(LocalDate desde, LocalDate hasta);
    List<Pelicula> buscarPeliByCal(int desde, int hasta);
    Pelicula altaPeli(Pelicula peli);
    Pelicula modiPeli(Pelicula peli, int id);
    Optional<Pelicula> buscarPeliById(int id);
    void addPersoAPeli(Personaje persoNuevo);
    void removePersoDePeli(Personaje persoViejo);
    Optional<Pelicula> buscarPeliculaByTitulo(String titulo);
}
