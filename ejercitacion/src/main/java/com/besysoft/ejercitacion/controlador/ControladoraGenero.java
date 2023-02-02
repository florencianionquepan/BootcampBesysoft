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

    private ResponseEntity<?> notSuccessResponse(String mensaje,int id){
        mensajeBody.put("Success",Boolean.FALSE);
        mensajeBody.put("data", String.format(mensaje,id));
        return ResponseEntity
                .badRequest()
                .body(mensajeBody);
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
        if(this.genService.existeNombre(genero)){
            return this.notSuccessResponse("El genero ya existe",0);
        }
        if(!this.peliService.sonPelisCorrectas(genero.getListaPelis())){
            return this.notSuccessResponse("Alguna pelicula ingresada no existe o no es correcta",0);
        }
        Genero generoNuevo=this.genService.altaGenero(genero);
        return ResponseEntity.status(HttpStatus.CREATED).body(generoNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modiGenero(@RequestBody Genero genero,
                                         @PathVariable int id){
        this.genService.porSiListaPelisNull(genero);
        if(!this.genService.existeGenero(id)) {
            return this.notSuccessResponse("El genero con id %d ingresado no existe", id);
        }
        if(this.genService.existeNombreConOtroId(genero,id)){
            return this.notSuccessResponse("El genero ya existe",0);
        }
        if(!this.peliService.sonPelisCorrectas(genero.getListaPelis())){
            return this.notSuccessResponse("Alguna pelicula ingresada no existe",0);
        }
            Genero generoMod=this.genService.modiGenero(genero,id);
            mensajeBody.put("Success",Boolean.TRUE);
            mensajeBody.put("data",generoMod);
            return ResponseEntity.ok(mensajeBody);
    }



}
