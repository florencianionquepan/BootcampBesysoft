package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.Test;
import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/peliculas")
public class ControladoraPelicula {

    @GetMapping
    public List<Pelicula> verPelis(){
        Test miTest=new Test();
        miTest.generarDatos();
        return miTest.getListaPelis();
    }

    @GetMapping("/{titulo}")
    public List<Pelicula> buscarPeliByTitulo(@PathVariable String titulo){
        Test miTest=new Test();
        miTest.generarDatos();
        List<Pelicula> listaPelis=miTest.getListaPelis().stream()
                .filter(pelicula -> pelicula.getTitulo().equals(titulo))
                .collect(Collectors.toList());
        return listaPelis;
    }

    @GetMapping("/genero/{genero}")
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

    @GetMapping("/fechas")
    public List<Pelicula> buscarPeliFechas(@RequestParam @DateTimeFormat(pattern = "ddMMyyyy") LocalDate desde ,
                                            @RequestParam @DateTimeFormat(pattern = "ddMMyyyy") LocalDate hasta){
        Test miTest=new Test();
        miTest.generarDatos();
        List<Pelicula> listaPelis=miTest.getListaPelis().stream()
                .filter(pelicula -> pelicula.getFechaCreacion().isAfter(desde)
                        && pelicula.getFechaCreacion().isBefore(hasta))
                .collect(Collectors.toList());
        return listaPelis;
    }

    @GetMapping("/calificacion")
    public List<Pelicula> buscarPeliCalificacion(@RequestParam int desde,
                                           @RequestParam int hasta){
        Test miTest=new Test();
        miTest.generarDatos();
        List<Pelicula> listaPelis=miTest.getListaPelis().stream()
                .filter(pelicula -> pelicula.getCalificacion()<hasta
                        && pelicula.getCalificacion()>desde)
                .collect(Collectors.toList());
        return listaPelis;
    }
}
