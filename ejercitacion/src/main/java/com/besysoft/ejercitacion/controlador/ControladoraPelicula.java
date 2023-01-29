package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.utilidades.Test;
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
    public static List<Pelicula> listaPelis=Test.getListaPelis();
    public static List<Genero> listaGeneros=Test.getListaGeneros();
    public static List<Pelicula> getListaPelis() {
        return listaPelis;
    }

    public static void setListaPelis(List<Pelicula> listaPelis) {
        ControladoraPelicula.listaPelis = listaPelis;
    }

    public List<Genero> getListaGeneros() {
        return listaGeneros;
    }

    public void setListaGeneros(List<Genero> listaGeneros) {
        ControladoraPelicula.listaGeneros = listaGeneros;
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
        return this.successResponse(getListaPelis());
    }

    @GetMapping("/{titulo}")
    public ResponseEntity<?> buscarPeliByTitulo(@PathVariable String titulo){
        List<Pelicula> listaPelis=getListaPelis().stream()
                .filter(pelicula -> pelicula.getTitulo().equals(titulo))
                .collect(Collectors.toList());
        return this.successResponse(listaPelis);
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<?> buscarPeliByGenero(@PathVariable String genero){
        List<Genero> listaGeneros=getListaGeneros();
        List<List<Pelicula>> listaPelis= listaGeneros.stream()
                .filter(gen->gen.getNombre().equals(genero))
                .map(Genero::getListaPelis)
                .collect(Collectors.toList());
        List<Pelicula> listaPel=(listaPelis.size()>0)?listaPelis.get(0):new ArrayList<Pelicula>();
        return this.successResponse(listaPel);
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
            List<Pelicula> listaPelis = getListaPelis().stream()
                    .filter(pelicula -> pelicula.getFechaCreacion().isAfter(desde)
                            && pelicula.getFechaCreacion().isBefore(hasta))
                    .collect(Collectors.toList());
            return this.successResponse(listaPelis);
        }
    }

    @GetMapping("/calificacion")
    public ResponseEntity<?> buscarPeliCalificacion(@RequestParam int desde,
                                           @RequestParam int hasta){
        if(desde>hasta || desde<1 || hasta>5){
            return this.notSuccessResponse("Rango no válido",0);
        }
        List<Pelicula> listaPelis=getListaPelis().stream()
                .filter(pelicula -> pelicula.getCalificacion()<=hasta
                        && pelicula.getCalificacion()>=desde)
                .collect(Collectors.toList());
        return this.successResponse(listaPelis);
    }
    @PostMapping
    public ResponseEntity<?> altaPelicula(@RequestBody Pelicula peli){
        this.porSilistaPersoNull(peli);
        if(!ControladoraPersonaje.sonPersoCorrectos(peli.getListaPersonajes())){
            return this.notSuccessResponse("Algun personaje ingresado no existe",0);
        }
        peli.setId(getListaPelis().size()+1);
        getListaPelis().add(peli);
        setListaPelis(getListaPelis());
        //asociar esta pelicula a cada personaje que trae en su lista
        //Los personajes no se enteraron aun de la nueva peli
        ControladoraPersonaje.addPeliPerso(peli);
        return ResponseEntity.status(HttpStatus.CREATED).body(peli);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modiPelicula(@RequestBody Pelicula peli,
                                            @PathVariable int id){
        this.porSilistaPersoNull(peli);
        if(!this.existePeli(id)) {
            return this.notSuccessResponse("La pelicula con id %d ingresado no existe", id);
        }
        if(!ControladoraPersonaje.sonPersoCorrectos(peli.getListaPersonajes())){
            return this.notSuccessResponse("Algun personaje ingresado no existe",0);
        }
        getListaPelis().forEach(pel->{
            if(pel.getId()==id) {
                pel.setTitulo(peli.getTitulo());
                pel.setFechaCreacion(peli.getFechaCreacion());
                pel.setCalificacion(peli.getCalificacion());
                //Primero del lado de los personajes remuevo la peli vieja pel
                //y agrego la pelicula actualizada a los personajes que trae
                //O si cargo per directamente? Habr{a algun problema que todavia tenga la lista de pelis viejas?
                peli.setId(id);
                ControladoraPersonaje.removePeliPerso(pel);
                ControladoraPersonaje.addPeliPerso(peli);
                pel.setListaPersonajes(peli.getListaPersonajes());
            }
        });
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",getListaPelis().get(id-1));
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

    public boolean existePeli(int id){
        boolean existe;
        Optional<Pelicula> oPeli=getListaPelis()
                                    .stream()
                                    .filter(pel->pel.getId()==id)
                                    .findAny();
        existe=oPeli.isPresent();
        return existe;
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

    //Esto se agrega por si no tiene el key listaPersonajes, ya que sino da error en el servidor
    private void porSilistaPersoNull(Pelicula peli){
        if(peli.getListaPersonajes()==null){
            List<Personaje> listaPerVacia=new ArrayList<>();
            peli.setListaPersonajes(listaPerVacia);
        }
    }

}
