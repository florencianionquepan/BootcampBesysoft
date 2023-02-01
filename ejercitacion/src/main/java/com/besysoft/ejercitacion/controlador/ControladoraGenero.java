package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.servicios.interfaces.IGeneroService;
import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/generos")
public class ControladoraGenero {

    private final IGeneroService genService;
    private final IPeliculaService peliService;
    public Map<String,Object> mensajeBody= new HashMap<>();

    public ControladoraGenero(IGeneroService genService, IPeliculaService peliService) {
        this.genService = genService;
        this.peliService = peliService;
    }

    @GetMapping
    public ResponseEntity<?>  verGeneros(){
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",this.genService.verGeneros());
        return ResponseEntity.ok(mensajeBody);
    }

    @PostMapping
    public ResponseEntity<?> altaGenero(@RequestBody Genero genero){
        this.genService.porSiListaPelisNull(genero);
        if(!this.peliService.sonPelisCorrectas(genero.getListaPelis())){
            mensajeBody.put("Success", Boolean.FALSE);
            mensajeBody.put("data", "Alguna pelicula ingresada no existe o no es correcta");
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        Genero generoNuevo=this.genService.altaGenero(genero);
        return ResponseEntity.status(HttpStatus.CREATED).body(generoNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modiGenero(@RequestBody Genero genero,
                                         @PathVariable int id){
        this.genService.porSiListaPelisNull(genero);

        if(this.genService.existeGenero(id)) {
            mensajeBody.put("Success", Boolean.FALSE);
            mensajeBody.put("data", String.format("El genero con id %d ingresado no existe", id));
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        if(!this.peliService.sonPelisCorrectas(genero.getListaPelis())){
            mensajeBody.put("Success", Boolean.FALSE);
            mensajeBody.put("data", "Alguna pelicula ingresada no existe");
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
            Genero generoMod=this.genService.modiGenero(genero,id);
            mensajeBody.put("Success",Boolean.TRUE);
            mensajeBody.put("data",generoMod);
            return ResponseEntity.ok(mensajeBody);
    }



}
