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
    public ArrayList<Personaje> verPersonajes(){
        Test miTest=new Test();
        miTest.generarDatos();
        return miTest.getListaPerso();
    }

    @GetMapping("/personajes/nombre/{nombre}")
    public ArrayList<Personaje> buscarPersonajesByNombre(@PathVariable String nombre){
        Test miTest=new Test();
        miTest.generarDatos();
        List<Personaje> listaPerso=miTest.getListaPerso().stream()
                                        .filter(personaje -> personaje.getNombre().equals(nombre))
                                        .collect(Collectors.toList());
        ArrayList<Personaje> listaNueva=new ArrayList<Personaje>(listaPerso);
        return listaNueva;
    }

}
