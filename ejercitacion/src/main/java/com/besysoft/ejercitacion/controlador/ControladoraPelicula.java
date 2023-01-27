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
        peli.setId(getListaPelis().size()+1);
        }
        listaPelis.add(peli);
        //setListaPelis(listaPelis);
        return ResponseEntity.status(HttpStatus.CREATED).body(peli);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modiPelicula(@RequestBody Pelicula peli,
                                            @PathVariable int id){
        Optional<Pelicula> oPeli=getListaPelis()
                                .stream()
                                .filter(pel->pel.getId()==id)
                                .findAny();
        if(oPeli.isEmpty()) {
            mensajeBody.put("Success", Boolean.FALSE);
            mensajeBody.put("data", String.format("La pelicula con id %d ingresado no existe", id));
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        if(!this.existePersonaje(peli)){
            return this.notSuccessResponse("Algun personaje ingresado no existe",0);
        }
        getListaPelis().forEach(pel->{
            if(pel.getId()==id) {
                pel.setTitulo(peli.getTitulo());
                pel.setFechaCreacion(peli.getFechaCreacion());
                pel.setCalificacion(peli.getCalificacion());
                pel.setListaPersonajes(peli.getListaPersonajes());
            }
        });
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",getListaPelis().get(id-1));
        return ResponseEntity.ok(mensajeBody);
    }
    private boolean existePersonaje(Pelicula peli){
        //Si no envie ninguno en la peli, esto dara true
        boolean existe=peli.getListaPersonajes().size()==0;
        List<Personaje> listaPerso=ControladoraPersonaje.getListaPerso();
        for (Personaje per: peli.getListaPersonajes()){
            Optional<Personaje> oPerso = listaPerso.stream()
                    .filter(perso -> perso.getId() == per.getId())
                    .findAny();
            if(oPerso.isPresent()){
                existe=true;
            }
        }
        return existe;
    }

    public static boolean modificarPersonaje(Pelicula peliAnt,Pelicula peliActual,
                                             Personaje perAnt, Personaje perNuevo){
        setearPersonajes(peliAnt);
        setearPersonajes(peliActual);
        boolean removido=peliAnt.getListaPersonajes().remove(perAnt);
        System.out.println(peliAnt.getListaPersonajes());
        perNuevo.setId(perAnt.getId());
        boolean agregado=peliActual.getListaPersonajes().add(perNuevo);
        return removido && agregado;
    }

    private boolean tienePersoOtraPeli(Pelicula peliMod){
        boolean traePersoOtraPeli=true;
        List <Personaje> listaPersoTrae=peliMod.getListaPersonajes();
        for(Personaje per:listaPersoTrae){
            setearPelicula(per);
        }
        List <Personaje> listaPersoOtraPeli=listaPersoTrae.stream()
                .filter(perso->perso.getPelicula().getId()!=peliMod.getId())
                .collect(Collectors.toList());
        traePersoOtraPeli=listaPersoOtraPeli.size()>0;
        return traePersoOtraPeli;
    }

    private static void setearPersonajes(Pelicula peli){
        Optional <List<Personaje>> oLista=listaPelis.stream().filter(pel->pel.getId()==peli.getId())
                .map(Pelicula::getListaPersonajes).findAny();
        if(oLista.isPresent()){
            peli.setListaPersonajes(oLista.get());
        }
    }

    private void setearPelicula(Personaje per){
        Optional <Pelicula> oPeliAsociada=ControladoraPersonaje.getListaPerso().stream()
                .filter(perso->perso.getId()==per.getId())
                .map(Personaje::getPelicula).findAny();
        oPeliAsociada.ifPresent(per::setPelicula);
    }

    public static boolean sonPelisCorrectas(List<Pelicula> pelisIn){
        boolean sonCorrectas=pelisIn.size()==0;
        int contadorCorrectas=0;
        for(Pelicula peliIn:pelisIn){
            Optional <Pelicula> oPeliAsociada=getListaPelis().stream()
                                        .filter(peli->peli.getId()==peliIn.getId()).findAny();
            if(oPeliAsociada.isEmpty()){
                return false;
            }
            Pelicula aux = oPeliAsociada.get();
            aux.setListaPersonajes(null);
            contadorCorrectas=aux.equals(peliIn)?contadorCorrectas+1:contadorCorrectas;
        }
        return sonCorrectas;
    }
}
