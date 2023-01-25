package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.Test;
import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/peliculas")
public class ControladoraPelicula {
    public static List<Pelicula> listaPelis=Test.getListaPelis();
    public static List<Genero> listaGeneros=Test.getListaGeneros();
    public static List<Pelicula> getListaPelis() {
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
        List<List<Pelicula>> listaPelis= listaGeneros.stream()
                .filter(gen->gen.getNombre().equals(genero))
                .map(Genero::getListaPelis)
                .collect(Collectors.toList());
        List<Pelicula> listaPel=(listaPelis.size()>0)?listaPelis.get(0):new ArrayList<Pelicula>();
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

    @PutMapping("/{id}")
    public ResponseEntity<?> modiPelicula(@RequestBody Pelicula peli,
                                            @PathVariable int id){
        Optional<Pelicula> oPeli=this.getListaPelis()
                                .stream()
                                .filter(pel->pel.getId()==id)
                                .findAny();
        if(!oPeli.isPresent()) {
            mensajeBody.put("Success", Boolean.FALSE);
            mensajeBody.put("data", String.format("La pelicula con id %d ingresado no existe", id));
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        if(!this.existePersonaje(peli)){
            mensajeBody.put("Success", Boolean.FALSE);
            mensajeBody.put("data", "Algun personaje ingresado no existe");
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        this.getListaPelis().forEach(pel->{
            if(pel.getId()==id) {
                pel.setTitulo(peli.getTitulo());
                pel.setFechaCreacion(peli.getFechaCreacion());
                pel.setCalificacion(peli.getCalificacion());
                pel.setListaPersonajes(peli.getListaPersonajes());
            }
        });
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",this.getListaPelis().get(id-1));
        return ResponseEntity.ok(mensajeBody);
    }
    private boolean existePersonaje(Pelicula peli){
        //Si no envie ninguno en la peli, esto dara true
        boolean existe=peli.getListaPersonajes().size()==0;
        List<Personaje> listaPerso=ControladoraPersonaje.getListaPerso();
        for (Personaje per: peli.getListaPersonajes()){
            Optional<Personaje> oPerso = listaPerso.stream()
                    .filter(perso -> perso.getId() == per.getId())
                    .findAny();
            if(oPerso.isPresent()){
                existe=true;
            }
        }
        return existe;
    }
}
