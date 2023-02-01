package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import com.besysoft.ejercitacion.servicios.interfaces.IPersonajeService;
import com.besysoft.ejercitacion.dominio.Personaje;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/personajes")
public class ControladoraPersonaje {

    private final IPersonajeService persoService;
    private final IPeliculaService peliService;

    public ControladoraPersonaje(IPersonajeService service, IPeliculaService peliService){
        this.persoService = service;
        this.peliService = peliService;
    }
    public Map<String,Object> mensajeBody= new HashMap<>();

    private ResponseEntity<?> successResponse(List<?> lista){
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",lista);
        return ResponseEntity.ok(mensajeBody);
    }

    private ResponseEntity<?> notSuccessResponse(String mensaje, int id){
        mensajeBody.put("Success",Boolean.FALSE);
        mensajeBody.put("data",String.format(mensaje,id));
        return ResponseEntity
                .badRequest()
                .body(mensajeBody);
    }

    @GetMapping
    public ResponseEntity<?> verPerso(){
        return this.successResponse(this.persoService.verPerso());
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<?> buscarPersoByNombre(@PathVariable String nombre){
        return this.successResponse(this.persoService.buscarPersoByNombre(nombre));
    }

    @GetMapping("/edad/{edad}")
    public ResponseEntity<?> buscarPersoByEdad(@PathVariable int edad){
        return this.successResponse(this.persoService.buscarPersoByEdad(edad));
    }

    @GetMapping("/edad")
    public ResponseEntity<?> buscarPersoRangoEdad(@RequestParam int desde,
                                                 @RequestParam int hasta){
        if(desde>hasta){
            return this.notSuccessResponse("Rango de edad no válido",0);
        }
        return this.successResponse(this.persoService.buscarPersoRangoEdad(desde,hasta));
    }

    @PostMapping
    public ResponseEntity<?> altaPersonaje(@RequestBody Personaje perso){
        Personaje person=this.persoService.porSiListaPelisNull(perso);
        if(!this.peliService.sonPelisCorrectas(person.getListaPeliculas())){
            return this.notSuccessResponse("ALguna pelicula asociada no existe",0);
        }
        Personaje personaje=this.persoService.altaPersonaje(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(personaje);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modiPerso(@RequestBody Personaje perso,
                                       @PathVariable int id){
        Personaje person=this.persoService.porSiListaPelisNull(perso);
        if(!this.persoService.existePerso(id)) {
            return this.notSuccessResponse("El personaje con id %d ingresado no existe", id);
        }
        if(!this.peliService.sonPelisCorrectas(person.getListaPeliculas())) {
            return this.notSuccessResponse("Alguna pelicula asociada no existe",0);
        }
        Personaje personaje=this.persoService.modiPersonaje(person,id);
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",personaje);
        return ResponseEntity.ok(mensajeBody);
    }


}
