package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.dominio.Pelicula;
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
    public static List<Personaje> listaPerso=Test.getListaPerso();
    public static List<Personaje> getListaPerso() {
        return listaPerso;
    }
    public void setListaPerso(List<Personaje> listaPerso) {
        ControladoraPersonaje.listaPerso = listaPerso;
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
        return this.successResponse(getListaPerso());
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<?> buscarPersoByNombre(@PathVariable String nombre){
        List<Personaje> listaPerso=getListaPerso().stream()
                                        .filter(personaje -> personaje.getNombre().equals(nombre))
                                        .collect(Collectors.toList());
        return this.successResponse(listaPerso);
    }

    @GetMapping("/edad/{edad}")
    public ResponseEntity<?> buscarPersoByEdad(@PathVariable int edad){
        List<Personaje> listaPerso=getListaPerso().stream()
                .filter(personaje -> personaje.getEdad()==edad)
                .collect(Collectors.toList());
        return this.successResponse(listaPerso);
    }

    @GetMapping("/edad")
    public ResponseEntity<?> buscarPersoRangoEdad(@RequestParam int desde,
                                                 @RequestParam int hasta){
        if(desde>hasta){
            this.notSuccessResponse("Rango de edad no válido",0);
        }
        List<Personaje> listaPerso=getListaPerso().stream()
                .filter(per -> per.getEdad()<hasta && per.getEdad()>desde)
                .collect(Collectors.toList());
        return this.successResponse(listaPerso);

    }

    @PostMapping
    public ResponseEntity<?> altaPersonaje(@RequestBody Personaje perso){
        if(!ControladoraPelicula.sonPelisCorrectas(perso.getListaPeliculas())){
            this.notSuccessResponse("ALguna pelicula asociada no existe",0);
        }
        perso.setId(getListaPerso().size()+1);
        getListaPerso().add(perso);
        setListaPerso(getListaPerso());
        //asociar este personaje a cada pelicula que trae en la lista.
        // LaS peliculaS no se enterARON de este personaje todavia:
        //Setearle a laS peliculaS su lista de personajes con el nuevo personaje
        ControladoraPelicula.addPersoPeliculas(perso);
        return ResponseEntity.status(HttpStatus.CREATED).body(perso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modiPerso(@RequestBody Personaje perso,
                                       @PathVariable int id){
        Optional<Personaje> oPerso=getListaPerso()
                                    .stream()
                                    .filter(pel->pel.getId()==id)
                                    .findAny();
        if(oPerso.isEmpty()) {
            return this.notSuccessResponse("El personaje con id %d ingresado no existe", id);
        }
        if(!ControladoraPelicula.sonPelisCorrectas(perso.getListaPeliculas())) {
            this.notSuccessResponse("Alguna pelicula asociada no existe",0);
        }
        getListaPerso().forEach(per->{
            if(per.getId()==id) {
                per.setNombre(perso.getNombre());
                per.setEdad(perso.getEdad());
                per.setHistoria(perso.getHistoria());
                per.setPeso(perso.getPeso());
                //Primero del lado de las peliculas remuevo el personaje anterior
                //y agrego el personaje actual en las que trae. Para eso debo setearle el id.
                //O si cargo per directamente? Habr{a algun problema que todavia tenga la lista de pelis viejas?
                perso.setId(id);
                ControladoraPelicula.removePersoPeliculas(per);
                ControladoraPelicula.addPersoPeliculas(perso);
                per.setListaPeliculas(perso.getListaPeliculas());
            }
        });
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",getListaPerso().get(id-1));
        return ResponseEntity.ok(mensajeBody);
    }

/*  ESTE LO USABA EN PELICULA
    public void actualizarPerso(Personaje per,Pelicula peliNueva){
        List <Personaje> persoOtros=getListaPerso().stream().filter(perso->perso.getId()!=per.getId())
                                    .collect(Collectors.toList());
        per.setPelicula(peliNueva);
        persoOtros.add(per);
        this.setListaPerso(persoOtros);
    }*/

    public boolean sonPersoCorrectos(List<Personaje> persosIn){
        //Si no envie ninguno en la peli, esto dara true
        boolean sonCorrectos;
        int contadorCorrectos=0;
        for (Personaje per: persosIn){
            Optional<Personaje> oPerso = listaPerso.stream()
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
