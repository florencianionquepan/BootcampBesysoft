package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.Test;
import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/generos")
public class ControladoraGenero {
    public Map<String,Object> mensajeBody= new HashMap<>();
    public static List<Genero> listaGeneros= Test.getListaGeneros();
    public List<Genero> getListaGeneros() {
        return listaGeneros;
    }
    public void setListaGeneros(List<Genero> listaGeneros) {
        this.listaGeneros = listaGeneros;
    }

    @PostMapping
    public ResponseEntity<?> altaGenero(@RequestBody Genero genero){
        //chequear PRIMERO que las peliculas asociadas existan
        List<Pelicula> listaPelis=ControladoraPelicula.getListaPelis();
        //Esto hacerlo por cada pelicula de la lista del genero:
        for (Pelicula peli : genero.getListaPelis()) {
            Optional<Pelicula> oPeliAsociada = listaPelis.stream()
                                                .filter(p -> p.getId() == peli.getId())
                                                .findAny();
            if (!oPeliAsociada.isPresent()) {
                mensajeBody.put("Success", Boolean.FALSE);
                mensajeBody.put("data", String.format("La pelicula cuyo id %d no existe",peli.getId())); //para darle formato al string que voy a armar
                return ResponseEntity.badRequest().body(mensajeBody);
            }else{
                peli.setListaPersonajes(oPeliAsociada.get().getListaPersonajes());
            }
        }
        genero.setId(this.listaGeneros.size()+1);
        this.getListaGeneros().add(genero);
        this.setListaGeneros(listaGeneros);
        return ResponseEntity.status(HttpStatus.CREATED).body(genero);
    }
    @GetMapping
    private ResponseEntity<?>  verGeneros(){
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",listaGeneros);
        return ResponseEntity.ok(mensajeBody);
    }
}
