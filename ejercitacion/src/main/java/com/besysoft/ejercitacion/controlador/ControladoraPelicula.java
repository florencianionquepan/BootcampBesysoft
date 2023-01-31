package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import com.besysoft.ejercitacion.servicios.interfaces.IPersonajeService;
import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/peliculas")
public class ControladoraPelicula {

    private final IPeliculaService peliService;
    private final IPersonajeService persoService;

    public ControladoraPelicula(IPeliculaService peliService, IPersonajeService persoService) {
        this.peliService = peliService;
        this.persoService = persoService;
    }

    public Map<String,Object> mensajeBody= new HashMap<>();

    private ResponseEntity<?> successResponse(List<?> lista){
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",lista);
        return ResponseEntity.ok(mensajeBody);
    }

    private ResponseEntity<?> notSuccessResponse(String mensaje,int id){
        mensajeBody.put("Success",Boolean.FALSE);
        mensajeBody.put("data", String.format(mensaje,id));
        return ResponseEntity
                .badRequest()
                .body(mensajeBody);
    }

    @GetMapping
    public ResponseEntity<?> verPelis(){
        return this.successResponse(this.peliService.verPelis());
    }

    @GetMapping("/{titulo}")
    public ResponseEntity<?> buscarPeliByTitulo(@PathVariable String titulo){
        return this.successResponse(this.peliService.buscarPeliByTitulo(titulo));
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<?> buscarPeliByGenero(@PathVariable String genero){
        return this.successResponse(this.peliService.buscarPeliByGenero(genero));
    }

    @GetMapping("/fechas")
    public ResponseEntity<?> buscarPeliFechas(@RequestParam @DateTimeFormat(pattern = "ddMMyyyy") LocalDate desde ,
                                            @RequestParam @DateTimeFormat(pattern = "ddMMyyyy") LocalDate hasta){
        if(desde.isAfter(hasta) || hasta.isBefore(desde)){
            mensajeBody.put("Success",Boolean.FALSE);
            mensajeBody.put("data", String.format("Las fechas desde %tF " +
                            "hasta %tF no conforman un rango válido",desde,hasta));
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }else {
            return this.successResponse(this.peliService.buscarPeliByFechas(desde,hasta));
        }
    }

    @GetMapping("/calificacion")
    public ResponseEntity<?> buscarPeliCalificacion(@RequestParam int desde,
                                           @RequestParam int hasta){
        if(desde>hasta || desde<1 || hasta>5){
            return this.notSuccessResponse("Rango no válido",0);
        }
        return this.successResponse(this.peliService.buscarPeliByCal(desde,hasta));
    }
    @PostMapping
    public ResponseEntity<?> altaPelicula(@RequestBody Pelicula peli){
        Pelicula pelicu=this.peliService.porSiListaPersoNull(peli);
        if(!this.persoService.sonPersoCorrectos(pelicu.getListaPersonajes())){
            return this.notSuccessResponse("Algun personaje ingresado no existe",0);
        }
        Pelicula pelicula=this.peliService.altaPeli(pelicu);
        //asociar esta pelicula a cada personaje que trae en su lista
        //Los personajes no se enteraron aun de la nueva peli
        //this.persoService.addPerso();
        return ResponseEntity.status(HttpStatus.CREATED).body(pelicula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modiPelicula(@RequestBody Pelicula peli,
                                            @PathVariable int id){
        Pelicula pelicu=this.peliService.porSiListaPersoNull(peli);
        if(!this.peliService.existePeli(id)) {
            return this.notSuccessResponse("La pelicula con id %d ingresado no existe", id);
        }
        if(!persoService.sonPersoCorrectos(pelicu.getListaPersonajes())){
            return this.notSuccessResponse("Algun personaje ingresado no existe",0);
        }
        //this.persoService.addPerso();
        //
        Pelicula peliM=this.peliService.modiPeli(pelicu,id);
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",peliM);
        return ResponseEntity.ok(mensajeBody);
    }

    public static boolean sonPelisCorrectas(List<Pelicula> pelisIn){
        boolean sonCorrectas;
        int contadorCorrectas=0;
        for(Pelicula peliIn:pelisIn){
            Optional <Pelicula> oPeliAsociada=getListaPelis().stream()
                                        .filter(peli->peli.getId()==peliIn.getId()).findAny();
            if(oPeliAsociada.isEmpty()){
                return false;
            }
            //Luego ver si al equals le puedo borrar la lista de personajes si no lo voy a necesitar en otro lado
            List<Personaje> guardoPerso=oPeliAsociada.get().getListaPersonajes();
            Pelicula aux = oPeliAsociada.get();
            aux.setListaPersonajes(null);
            contadorCorrectas=aux.equals(peliIn)?contadorCorrectas+1:contadorCorrectas;
            aux.setListaPersonajes(guardoPerso);
        }
        sonCorrectas=contadorCorrectas==pelisIn.size();
        return sonCorrectas;
    }

    public static void addPersoPeliculas(Personaje perso) {
        for(Pelicula peli: perso.getListaPeliculas()){
            List<Personaje> listaPersoPeli=getListaPelis().stream()
                    .filter(pel->pel.getId()==peli.getId())
                    .map(Pelicula::getListaPersonajes).findAny().get();
            listaPersoPeli.add(perso);
            peli.setListaPersonajes(listaPersoPeli);
        }
    }

    public static void removePersoPeliculas(Personaje perAnterior){
        for(Pelicula peli: perAnterior.getListaPeliculas()){
            List<Personaje> listaPerso=getListaPelis().stream()
                    .filter(pel->pel.getId()==peli.getId())
                    .map(Pelicula::getListaPersonajes).findAny().get();
            listaPerso.remove(perAnterior);
            peli.setListaPersonajes(listaPerso);
        }
    }

}
