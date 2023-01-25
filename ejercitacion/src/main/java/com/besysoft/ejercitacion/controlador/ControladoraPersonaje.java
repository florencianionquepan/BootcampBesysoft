package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.Test;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/personajes")
public class ControladoraPersonaje {
    public List<Personaje> listaPerso=Test.getListaPerso();
    public List<Personaje> getListaPerso() {
        return listaPerso;
    }
    public void setListaPerso(List<Personaje> listaPerso) {
        this.listaPerso = listaPerso;
    }
    public Map<String,Object> mensajeBody= new HashMap<>();

    private ResponseEntity<?> successResponse(List<?> lista){
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",lista);
        return ResponseEntity.ok(mensajeBody);
    }

    private List<Personaje> buscarPersoByPeli(Pelicula peli){
        List <Personaje> listaPerso=this.listaPerso.stream()
                        .filter(perso->perso.getPelicula().getId()==peli.getId())
                        .collect(Collectors.toList());
        return listaPerso;
    }
    @GetMapping
    public ResponseEntity<?> verPerso(){
        return this.successResponse(this.listaPerso);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<?> buscarPersoByNombre(@PathVariable String nombre){
        List<Personaje> listaPerso=this.listaPerso.stream()
                                        .filter(personaje -> personaje.getNombre().equals(nombre))
                                        .collect(Collectors.toList());
        return this.successResponse(listaPerso);
    }

    @GetMapping("/edad/{edad}")
    public ResponseEntity<?> buscarPersoByEdad(@PathVariable int edad){
        List<Personaje> listaPerso=this.listaPerso.stream()
                .filter(personaje -> personaje.getEdad()==edad)
                .collect(Collectors.toList());
        return this.successResponse(listaPerso);
    }

    @GetMapping("/edad")
    public ResponseEntity<?> buscarPersoRangoEdad(@RequestParam int desde,
                                                 @RequestParam int hasta){
        if(desde>hasta || hasta<desde){
            mensajeBody.put("Success",Boolean.FALSE);
            mensajeBody.put("data","Rango de edad no válido");
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        List<Personaje> listaPerso=this.listaPerso.stream()
                .filter(per -> per.getEdad()<hasta && per.getEdad()>desde)
                .collect(Collectors.toList());
        return this.successResponse(listaPerso);

    }

    @PostMapping
    public ResponseEntity<?> altaPersonaje(@RequestBody Personaje perso){
        perso.setId(this.listaPerso.size()+1);

        this.listaPerso.add(perso);
        this.setListaPerso(this.listaPerso);
        //asociar este personaje a la peliculA. La pelicula no se entero de este personaje todavia:
        //Aca primero dejar el optional por si el personaje se crea con una pelicula cuyo id no existe!!
        //y enviar un error de bad request. Luego si existe ahi si hacer el get y setearle la nueva lista de personajes.Esto
        //hacerlo primero entonces!!
        Pelicula peliAsociada=ControladoraPelicula.getListaPelis().
                stream().filter(peli->peli.getId()==perso.getPelicula().getId()).findAny().get();
        //Asociarle los personajes de la pelicula nada massss
        List<Personaje> listaPer=this.buscarPersoByPeli(peliAsociada);
        peliAsociada.setListaPersonajes(listaPer);
        return ResponseEntity.status(HttpStatus.CREATED).body(perso);
    }



}
