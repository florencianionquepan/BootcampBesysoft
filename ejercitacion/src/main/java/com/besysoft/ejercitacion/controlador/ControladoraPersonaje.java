package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.Test;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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



}
