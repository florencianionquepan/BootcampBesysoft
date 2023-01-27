package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.Test;
import com.besysoft.ejercitacion.dominio.Genero;
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

    private List<Personaje> buscarPersoByPeli(Pelicula peli){
        List <Personaje> listaPerson=getListaPerso().stream()
                        .filter(perso->perso.getPelicula().getId()==peli.getId())
                        .collect(Collectors.toList());
        return listaPerson;
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
            mensajeBody.put("Success",Boolean.FALSE);
            mensajeBody.put("data","Rango de edad no válido");
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        List<Personaje> listaPerso=getListaPerso().stream()
                .filter(per -> per.getEdad()<hasta && per.getEdad()>desde)
                .collect(Collectors.toList());
        return this.successResponse(listaPerso);

    }

    @PostMapping
    public ResponseEntity<?> altaPersonaje(@RequestBody Personaje perso){
        //Primero chequeo que la pelicula asociada al personaje exista:
        Optional <Pelicula> oPeliAsociada=ControladoraPelicula.getListaPelis().
                stream().filter(peli->peli.getId()==perso.getPelicula().getId()).findAny();
        if(oPeliAsociada.isEmpty()){
            mensajeBody.put("Success",Boolean.FALSE);
            mensajeBody.put("data","La pelicula asociada no existe");
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        perso.setId(getListaPerso().size()+1);
        getListaPerso().add(perso);
        this.setListaPerso(getListaPerso());
        //asociar este personaje a la pelicula. La pelicula no se entero de este personaje todavia:
        //Setearle a la pelicula su lista de personajes con el nuevo personaje
        Pelicula peliAsociada=oPeliAsociada.get();
        List<Personaje> listaPer=this.buscarPersoByPeli(peliAsociada);
        peliAsociada.setListaPersonajes(listaPer);
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
            mensajeBody.put("Success", Boolean.FALSE);
            mensajeBody.put("data", String.format("El personaje con id %d ingresado no existe", id));
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        if(!existePeli(perso)) {
            mensajeBody.put("Success", Boolean.FALSE);
            mensajeBody.put("data", String.format("La pelicula con id %d no existe", perso.getPelicula().getId()));
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        getListaPerso().forEach(per->{
            if(per.getId()==id) {
                per.setNombre(perso.getNombre());
                per.setEdad(perso.getEdad());
                per.setHistoria(perso.getHistoria());
                per.setPeso(perso.getPeso());
                Pelicula peliAnterior=per.getPelicula();
                Pelicula peliActual=perso.getPelicula();
                per.setPelicula(peliActual);
                //y a la pelicula le modifico el personaje
                ControladoraPelicula.modificarPersonaje(peliAnterior,peliActual,per,perso);
            }
        });
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",getListaPerso().get(id-1));
        return ResponseEntity.ok(mensajeBody);
    }

    private boolean existePeli(Personaje perso){
        boolean existe=false;
        List<Pelicula> listaPelis=ControladoraPelicula.getListaPelis();
        Optional<Pelicula> oPeliAs = listaPelis.stream()
                                        .filter(p -> p.getId() == perso.getPelicula().getId())
                                        .findAny();
            if(oPeliAs.isPresent()){
                existe=true;
            }
        return existe;
    }

    public void actualizarPerso(Personaje per,Pelicula peliNueva){
        List <Personaje> persoOtros=getListaPerso().stream().filter(perso->perso.getId()!=per.getId())
                                    .collect(Collectors.toList());
        per.setPelicula(peliNueva);
        persoOtros.add(per);
        this.setListaPerso(persoOtros);
    }


}
