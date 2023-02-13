package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.dto.GeneroReqDTO;
import com.besysoft.ejercitacion.dto.GeneroRespDTO;
import com.besysoft.ejercitacion.dto.mapper.GeneroMapper;
import com.besysoft.ejercitacion.dto.mapper.IGeneroMapper;
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
    private final IGeneroMapper genMap;
    public Map<String,Object> mensajeBody= new HashMap<>();

    public ControladoraGenero(IGeneroService genService, IPeliculaService peliService, IGeneroMapper genMap) {
        this.genService = genService;
        this.peliService = peliService;
        this.genMap = genMap;
    }

    private ResponseEntity<?> notSuccessResponse(String mensaje,int id){
        mensajeBody.put("Success",Boolean.FALSE);
        mensajeBody.put("data", String.format(mensaje,id));
        return ResponseEntity
                .badRequest()
                .body(mensajeBody);
    }

    @GetMapping
    private ResponseEntity<?>  verGeneros(){
        List<GeneroRespDTO> genRespDto=this.genMap.mapListToDto(this.genService.verGeneros());
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",genRespDto);
        return ResponseEntity.ok(mensajeBody);
    }

    @PostMapping
    public ResponseEntity<?> altaGenero(@RequestBody GeneroReqDTO generoReq){
        Genero genero=genMap.mapToEntity(generoReq);
        this.genService.porSiListaPelisNull(genero);
        if(this.genService.existeNombre(genero)){
            return this.notSuccessResponse("El genero ya existe",0);
        }
        if(!this.peliService.sonPelisCorrectas(genero.getListaPelis())){
            return this.notSuccessResponse("Alguna pelicula ingresada no existe o no es correcta",0);
        }
        Genero generoNuevo=this.genService.altaGenero(genero);
        GeneroRespDTO genDto=this.genMap.mapToDto(generoNuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(genDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modiGenero(@RequestBody GeneroReqDTO generoReq,
                                         @PathVariable int id){
        Genero genero=genMap.mapToEntity(generoReq);
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
            GeneroRespDTO genDto=this.genMap.mapToDto(generoMod);
            mensajeBody.put("Success",Boolean.TRUE);
            mensajeBody.put("data",genDto);
            return ResponseEntity.ok(mensajeBody);
    }



}
