package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.Test;
import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ControladoraPelicula {

    @GetMapping("/peliculas")
    public List<Pelicula> verPelis(){
        Test miTest=new Test();
        miTest.generarDatos();
        return miTest.getListaPelis();
    }

    @GetMapping("/peliculas/{titulo}")
    public List<Pelicula> buscarPeliByTitulo(@PathVariable String titulo){
        Test miTest=new Test();
        miTest.generarDatos();
        List<Pelicula> listaPelis=miTest.getListaPelis().stream()
                .filter(pelicula -> pelicula.getTitulo().equals(titulo))
                .collect(Collectors.toList());
        return listaPelis;
    }

    @GetMapping("/peliculas/genero/{genero}")
    public List<Pelicula> buscarPeliByGenero(@PathVariable String genero){
        Test miTest=new Test();
        miTest.generarDatos();
        List<Genero> listaGeneros=miTest.getListaGeneros();
        List<ArrayList<Pelicula>> listaPelis= listaGeneros.stream()
                .filter(gen->gen.getNombre().equals(genero))
                .map(Genero::getListaPelis)
                .collect(Collectors.toList());
        ArrayList<Pelicula> listaPel=(listaPelis.size()>0)?listaPelis.get(0):new ArrayList<Pelicula>();
        return listaPel;
    }
}
