package com.besysoft.ejercitacion.repositorios;

import com.besysoft.ejercitacion.dominio.Pelicula;

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
}