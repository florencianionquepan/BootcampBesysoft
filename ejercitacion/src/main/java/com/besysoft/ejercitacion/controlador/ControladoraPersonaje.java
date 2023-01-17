package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.Test;
import com.besysoft.ejercitacion.dominio.Personaje;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ControladoraPersonaje {
    Test miTest=new Test();
    @GetMapping("/personajes")
    public ArrayList<Personaje> verPersonajes(){
        miTest.generarDatos();
        return miTest.getListaPerso();
    }
    
}
