package com.besysoft.ejercitacion.servicios.interfaces;

import com.besysoft.ejercitacion.dominio.Pelicula;

import java.time.LocalDate;
import java.util.List;

public interface IPeliculaService {
    List<Pelicula> verPelis();
    List<Pelicula> buscarPeliByTitulo(String titulo);
    List<Pelicula> buscarPeliByGenero(String genero);
    List<Pelicula> buscarPeliByFechas(LocalDate desde, LocalDate hasta);
    List<Pelicula> buscarPeliByCal(int desde, int hasta);
    Pelicula altaPeli(Pelicula peli);
    Pelicula modiPeli(Pelicula peli, int id);
    boolean sonPelisCorrectas(List<Pelicula> pelisIn);
    boolean existePeli(int id);
}
