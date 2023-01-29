package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.utilidades.Test;
import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/generos")
public class ControladoraGenero {
    public Map<String,Object> mensajeBody= new HashMap<>();
    public static List<Genero> listaGeneros= Test.getListaGeneros();
    public List<Genero> getListaGeneros() {
        return listaGeneros;
    }
    public void setListaGeneros(List<Genero> listaGeneros) {
        ControladoraGenero.listaGeneros = listaGeneros;
    }

    @PostMapping
    public ResponseEntity<?> altaGenero(@RequestBody Genero genero){
        this.porSiListaPelisNull(genero);
        if(!ControladoraPelicula.sonPelisCorrectas(genero.getListaPelis())){
            mensajeBody.put("Success", Boolean.FALSE);
            mensajeBody.put("data", "Alguna pelicula ingresada no existe o no es correcta");
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        genero.setId(getListaGeneros().size()+1);
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
        this.porSiListaPelisNull(genero);
        Optional<Genero> oGenero=this.getListaGeneros()
                                .stream()
                                .filter(gen->gen.getId()==id)
                                .findAny();
        if(oGenero.isEmpty()) {
            mensajeBody.put("Success", Boolean.FALSE);
            mensajeBody.put("data", String.format("El genero con id %d ingresado no existe", id));
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        if(!ControladoraPelicula.sonPelisCorrectas(genero.getListaPelis())){
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

    private void porSiListaPelisNull(Genero genero){
        if(genero.getListaPelis()==null){
            List<Pelicula> listaPelis=new ArrayList<>();
            genero.setListaPelis(listaPelis);
        }
    }


}
