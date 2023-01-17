package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.Test;
import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ControladoraPelicula {

    @GetMapping("/peliculas")
    public ArrayList<Pelicula> verPelis(){
        Test miTest=new Test();
        miTest.generarDatos();
        return miTest.getListaPelis();
    }

    @GetMapping("/peliculas/{titulo}")
    public ArrayList<Pelicula> buscarPeliByTitulo(@PathVariable String titulo){
        Test miTest=new Test();
        miTest.generarDatos();
        List<Pelicula> listaPelis=miTest.getListaPelis().stream()
                .filter(pelicula -> pelicula.getTitulo().equals(titulo))
                .collect(Collectors.toList());
        ArrayList<Pelicula> listaNueva=new ArrayList<Pelicula>(listaPelis);
        return listaNueva;
    }

    @GetMapping("/peliculas/genero/{genero}")
    public ArrayList<Pelicula> buscarPeliByGenero(@PathVariable String genero){
        Test miTest=new Test();
        miTest.generarDatos();
        ArrayList<Genero> listaGeneros=miTest.getListaGeneros();
        ArrayList<Pelicula> listaPelis= listaGeneros.stream()
                .filter(gen->gen.getNombre().equals(genero))
                .map(Genero::getListaPelis)
                .collect(Collectors.toList()).get(0);
        return listaPelis;
    }
}
