package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.Test;
import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/peliculas")
public class ControladoraPelicula {
    public List<Pelicula> listaPelis=Test.getListaPelis();
    public List<Genero> listaGeneros=Test.getListaGeneros();
    public List<Pelicula> getListaPelis() {
        return listaPelis;
    }

    public void setListaPelis(List<Pelicula> listaPelis) {
        this.listaPelis = listaPelis;
    }

    public List<Genero> getListaGeneros() {
        return listaGeneros;
    }

    public void setListaGeneros(List<Genero> listaGeneros) {
        this.listaGeneros = listaGeneros;
    }

    public Map<String,Object> mensajeBody= new HashMap<>();

    private ResponseEntity<?> successResponse(List<?> lista){
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",lista);
        return ResponseEntity.ok(mensajeBody);
    }
    @GetMapping
    public ResponseEntity<?> verPelis(){
        return this.successResponse(this.listaPelis);
    }

    @GetMapping("/{titulo}")
    public ResponseEntity<?> buscarPeliByTitulo(@PathVariable String titulo){

        List<Pelicula> listaPelis=this.listaPelis.stream()
                .filter(pelicula -> pelicula.getTitulo().equals(titulo))
                .collect(Collectors.toList());
        return this.successResponse(listaPelis);
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<?> buscarPeliByGenero(@PathVariable String genero){
        List<Genero> listaGeneros=this.listaGeneros;
        List<ArrayList<Pelicula>> listaPelis= listaGeneros.stream()
                .filter(gen->gen.getNombre().equals(genero))
                .map(Genero::getListaPelis)
                .collect(Collectors.toList());
        ArrayList<Pelicula> listaPel=(listaPelis.size()>0)?listaPelis.get(0):new ArrayList<Pelicula>();
        return this.successResponse(listaPel);
    }

    @GetMapping("/fechas")
    public ResponseEntity<?> buscarPeliFechas(@RequestParam @DateTimeFormat(pattern = "ddMMyyyy") LocalDate desde ,
                                            @RequestParam @DateTimeFormat(pattern = "ddMMyyyy") LocalDate hasta){
        if(desde.isAfter(hasta) || hasta.isBefore(desde)){
            mensajeBody.put("Success",Boolean.FALSE);
            mensajeBody.put("data",String.format("Las fechas desde %tF hasta %tF no conforman un rango válido",desde,hasta));
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }else {
            List<Pelicula> listaPelis = this.listaPelis.stream()
                    .filter(pelicula -> pelicula.getFechaCreacion().isAfter(desde)
                            && pelicula.getFechaCreacion().isBefore(hasta))
                    .collect(Collectors.toList());
            return this.successResponse(listaPelis);
        }
    }

    @GetMapping("/calificacion")
    public ResponseEntity<?> buscarPeliCalificacion(@RequestParam int desde,
                                           @RequestParam int hasta){
        if(desde>hasta || hasta<desde || desde<1 || hasta>5){
            mensajeBody.put("Success",Boolean.FALSE);
            mensajeBody.put("data","Rango no válido");
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        List<Pelicula> listaPelis=this.listaPelis.stream()
                .filter(pelicula -> pelicula.getCalificacion()<hasta
                        && pelicula.getCalificacion()>desde)
                .collect(Collectors.toList());
        return this.successResponse(listaPelis);
    }
    @PostMapping
    public ResponseEntity<?> altaPelicula(@RequestBody Pelicula peli){
        peli.setId(this.listaPelis.size()+1);
        this.listaPelis.add(peli);
        this.setListaPelis(this.listaPelis);
        return ResponseEntity.status(HttpStatus.CREATED).body(peli);
    }
}
