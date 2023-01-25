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
import java.util.stream.Collectors;

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
        if(!existePeli(genero)){
            mensajeBody.put("Success", Boolean.FALSE);
            mensajeBody.put("data", "Alguna pelicula ingresada no existe");
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        genero.setId(this.listaGeneros.size()+1);
        this.getListaGeneros().add(genero);
        this.setListaGeneros(listaGeneros);
        return ResponseEntity.status(HttpStatus.CREATED).body(genero);
    }
    @GetMapping
    public ResponseEntity<?>  verGeneros(){
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",listaGeneros);
        return ResponseEntity.ok(mensajeBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modiGenero(@RequestBody Genero genero,
                                         @PathVariable int id){
        Optional<Genero> oGenero=this.getListaGeneros()
                                .stream()
                                .filter(gen->gen.getId()==id)
                                .findAny();
        if(!oGenero.isPresent()) {
            mensajeBody.put("Success", Boolean.FALSE);
            mensajeBody.put("data", String.format("El genero con id %d ingresado no existe", id));
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        if(!this.existePeli(genero)){
            mensajeBody.put("Success", Boolean.FALSE);
            mensajeBody.put("data", "Alguna pelicula ingresada no existe");
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        this.getListaGeneros().forEach(gen->{
            if(gen.getId()==id) {
                gen.setNombre(genero.getNombre());
                gen.setListaPelis(genero.getListaPelis());
            }
        });
            mensajeBody.put("Success",Boolean.TRUE);
            mensajeBody.put("data",this.getListaGeneros().get(id-1));
            return ResponseEntity.ok(mensajeBody);
    }

    //Chequea si existe peli si es que se envia alguna en la lista del genero
    //Si la lista esta vacia lo toma como que existe
    private boolean existePeli(Genero genero){
        boolean existe=genero.getListaPelis().size()==0;
        List<Pelicula> listaPelis=ControladoraPelicula.getListaPelis();
        for (Pelicula peli : genero.getListaPelis()){
        Optional<Pelicula> oPeliAs = listaPelis.stream()
                                            .filter(p -> p.getId() == peli.getId())
                                            .findAny();
            //System.out.println(oPeliAs.get().equals(peli)); Esto me da falso
            if(oPeliAs.isPresent()){
                existe=true;
            }
        }
        return existe;
    }


}
