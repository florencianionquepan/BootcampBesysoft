package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.dto.GeneroReqDTO;
import com.besysoft.ejercitacion.dto.GeneroRespDTO;
import com.besysoft.ejercitacion.dto.mapper.IGeneroMapper;
import com.besysoft.ejercitacion.servicios.interfaces.IGeneroService;
import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/generos")
public class ControladoraGenero {

    private Logger logger=LoggerFactory.getLogger(ControladoraGenero.class);
    private final IGeneroService genService;
    private final IGeneroMapper genMap;
    public Map<String,Object> mensajeBody= new HashMap<>();

    public ControladoraGenero(IGeneroService genService, IGeneroMapper genMap) {
        this.genService = genService;
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
    public ResponseEntity<?>  verGeneros(){
        List<GeneroRespDTO> genRespDto=this.genMap.mapListToDto(this.genService.verGeneros());
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",genRespDto);
        return ResponseEntity.ok(mensajeBody);
    }

    @PostMapping
    public ResponseEntity<?> altaGenero(@RequestBody GeneroReqDTO generoReq){
        Genero genero=genMap.mapToEntity(generoReq);
        logger.info("genero a crear: "+genero);
        Genero generoNuevo=this.genService.altaGenero(genero);
        //Luego se implementaran excepciones y se devolvera el error especifico
        if(generoNuevo==null){
            return this.notSuccessResponse("El genero ya existe o alguna pelicula no existe",0);
        }
        GeneroRespDTO genDto=this.genMap.mapToDto(generoNuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(genDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modiGenero(@RequestBody GeneroReqDTO generoReq,
                                         @PathVariable int id){
        Genero genero=genMap.mapToEntity(generoReq);
        logger.info("genero a modificar: "+genero);
        Genero generoMod=this.genService.modiGenero(genero,id);
        if(generoMod==null){
            return this.notSuccessResponse("Existe un error en la peticion y el genero no pudo modificarse",0);
        }
        GeneroRespDTO genDto=this.genMap.mapToDto(generoMod);
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",genDto);
        return ResponseEntity.ok(mensajeBody);
    }



}
