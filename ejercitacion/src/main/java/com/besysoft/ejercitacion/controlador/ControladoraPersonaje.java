package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.servicios.interfaces.IPersonajeService;
import com.besysoft.ejercitacion.utilidades.Test;
import com.besysoft.ejercitacion.dominio.Personaje;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/personajes")
public class ControladoraPersonaje {

    private final IPersonajeService service;

    public ControladoraPersonaje(IPersonajeService service){
        this.service = service;
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
        return this.successResponse(this.service.verPerso());
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<?> buscarPersoByNombre(@PathVariable String nombre){
        return this.successResponse(this.service.buscarPersoByNombre(nombre));
    }

    @GetMapping("/edad/{edad}")
    public ResponseEntity<?> buscarPersoByEdad(@PathVariable int edad){
        return this.successResponse(this.service.buscarPersoByEdad(edad));
    }

    @GetMapping("/edad")
    public ResponseEntity<?> buscarPersoRangoEdad(@RequestParam int desde,
                                                 @RequestParam int hasta){
        if(desde>hasta){
            return this.notSuccessResponse("Rango de edad no válido",0);
        }
        return this.successResponse(this.service.buscarPersoRangoEdad(desde,hasta));
    }

    @PostMapping
    public ResponseEntity<?> altaPersonaje(@RequestBody Personaje perso){
        //falta refactorizar esta parte
        if(!ControladoraPelicula.sonPelisCorrectas(perso.getListaPeliculas())){
            return this.notSuccessResponse("ALguna pelicula asociada no existe",0);
        }
        Personaje person=this.service.altaPersonaje(perso);
        //asociar este personaje a cada pelicula que trae en la lista.
        // LaS peliculaS no se enterARON de este personaje todavia:
        //Setearle a laS peliculaS su lista de personajes con el nuevo personaje
        ControladoraPelicula.addPersoPeliculas(perso);
        return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modiPerso(@RequestBody Personaje perso,
                                       @PathVariable int id){
        Personaje person=this.service.modiPersonaje(perso,id);
        if(person==null) {
            return this.notSuccessResponse("El personaje con id %d ingresado no existe", id);
        }
        //falta refactorizar esta parte
        if(!ControladoraPelicula.sonPelisCorrectas(perso.getListaPeliculas())) {
            return this.notSuccessResponse("Alguna pelicula asociada no existe",0);
        }

        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",person);
        return ResponseEntity.ok(mensajeBody);
    }


    public static boolean sonPersoCorrectos(List<Personaje> persosIn){
        //Si no envie ninguno en la peli, esto dara true
        boolean sonCorrectos;
        int contadorCorrectos=0;
        for (Personaje per: persosIn){
            //aca se fija si existe el personaje
            Optional<Personaje> oPerso = this.service.verPerso().stream()
                                    .filter(perso -> perso.getId() == per.getId())
                                    .findAny();
            if(oPerso.isEmpty()){
                return false;
            }
            List<Pelicula> guardoLista=oPerso.get().getListaPeliculas();
            Personaje aux=oPerso.get();
            aux.setListaPeliculas(null);
            contadorCorrectos=aux.equals(per)?contadorCorrectos+1:contadorCorrectos;
            aux.setListaPeliculas(guardoLista);
        }
        sonCorrectos=contadorCorrectos==persosIn.size();
        return sonCorrectos;
    }


    public static void addPeliPerso(Pelicula peli) {
        for(Personaje person:peli.getListaPersonajes()){
            List<Pelicula> listaPeliculas=getListaPerso().stream()
                    .filter(per->per.getId()==person.getId())
                    .map(Personaje::getListaPeliculas).findAny().get();
            listaPeliculas.add(peli);
            person.setListaPeliculas(listaPeliculas);
        }
    }

    public static void removePeliPerso(Pelicula peliAnterior){
        for(Personaje person:peliAnterior.getListaPersonajes()){
            List<Pelicula> listaPel=getListaPerso().stream()
                    .filter(per->per.getId()==person.getId())
                    .map(Personaje::getListaPeliculas).findAny().get();
            listaPel.remove(peliAnterior);
            person.setListaPeliculas(listaPel);
        }
    }


}
