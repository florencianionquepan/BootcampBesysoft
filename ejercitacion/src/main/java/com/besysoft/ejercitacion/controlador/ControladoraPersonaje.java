package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.Test;
import com.besysoft.ejercitacion.dominio.Personaje;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ControladoraPersonaje {

    @GetMapping("/personajes")
    public List<Personaje> verPerso(){
        Test miTest=new Test();
        miTest.generarDatos();
        return miTest.getListaPerso();
    }

    @GetMapping("/personajes/{nombre}")
    public List<Personaje> buscarPersoByNombre(@PathVariable String nombre){
        Test miTest=new Test();
        miTest.generarDatos();
        List<Personaje> listaPerso=miTest.getListaPerso().stream()
                                        .filter(personaje -> personaje.getNombre().equals(nombre))
                                        .collect(Collectors.toList());
        return listaPerso;
    }

    @GetMapping("/personajes/edad/{edad}")
    public List<Personaje> buscarPersoByEdad(@PathVariable int edad){
        Test miTest=new Test();
        miTest.generarDatos();
        List<Personaje> listaPerso=miTest.getListaPerso().stream()
                .filter(personaje -> personaje.getEdad()==edad)
                .collect(Collectors.toList());
        return listaPerso;
    }

}
