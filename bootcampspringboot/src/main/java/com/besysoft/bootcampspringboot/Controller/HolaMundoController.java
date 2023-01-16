
package com.besysoft.bootcampspringboot.Controller;

import dominio.Persona;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaMundoController {
    
    @GetMapping()
    public String holaMundo(){
        return "Hola Mundo!";
    }
    
    @GetMapping("/saludar")
    public String saludar(){
        return "Hola, como estas";
    }
    
    @GetMapping("/saludar/{param}")
    //uso el name porque ahi yo llame param, no nombre!!
    public String saludarConParametros(@PathVariable(name="param") String nombre){
        return "Hola ".concat(nombre).concat(" Como estas");
    }
    
    @GetMapping("/saludar/nom-ape/{nombre}/{apellido}")
    public String saludarConNombreApellido(@PathVariable String nombre, 
                                            @PathVariable String apellido){
        boolean sonLetrasNombre = nombre.matches("^[a-zA-Z]+$");
        boolean sonLetrasApellido = apellido.matches("^[a-zA-Z]+$");
        String response="";
        
        if(sonLetrasNombre && sonLetrasApellido){
            response="Hola "+nombre+" "+apellido+" Como estas";
        }else{
            response="Valide nombre y apellido ingresados";
        }
        return response;
    }
    
    @GetMapping("/saludar/persona/{nombre}/{apellido}")
    public Persona armarPersona(@PathVariable String nombre, @PathVariable String apellido){
        Persona persona=new Persona(nombre,apellido);
        return persona;
    }
    
}
