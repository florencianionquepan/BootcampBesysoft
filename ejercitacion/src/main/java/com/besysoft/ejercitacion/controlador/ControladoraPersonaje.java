package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.Test;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/personajes")
public class ControladoraPersonaje {

    @GetMapping
    public List<Personaje> verPerso(){
        Test miTest=new Test();
        miTest.generarDatos();
        return miTest.getListaPerso();
    }

    @GetMapping("/{nombre}")
    public List<Personaje> buscarPersoByNombre(@PathVariable String nombre){
        Test miTest=new Test();
        miTest.generarDatos();
        List<Personaje> listaPerso=miTest.getListaPerso().stream()
                                        .filter(personaje -> personaje.getNombre().equals(nombre))
                                        .collect(Collectors.toList());
        return listaPerso;
    }

    @GetMapping("/edad/{edad}")
    public List<Personaje> buscarPersoByEdad(@PathVariable int edad){
        Test miTest=new Test();
        miTest.generarDatos();
        List<Personaje> listaPerso=miTest.getListaPerso().stream()
                .filter(personaje -> personaje.getEdad()==edad)
                .collect(Collectors.toList());
        return listaPerso;
    }

    @GetMapping("/edad")
    public List<Personaje> buscarPersoRangoEdad(@RequestParam int desde,
                                                 @RequestParam int hasta){
        Test miTest=new Test();
        miTest.generarDatos();
        List<Personaje> listaPerso=miTest.getListaPerso().stream()
                .filter(per -> per.getEdad()<hasta && per.getEdad()>desde)
                .collect(Collectors.toList());
        return listaPerso;
    }



}
