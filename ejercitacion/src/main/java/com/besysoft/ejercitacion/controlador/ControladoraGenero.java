package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.dto.GeneroReqDTO;
import com.besysoft.ejercitacion.dto.GeneroRespDTO;
import com.besysoft.ejercitacion.dto.mapper.IGeneroMapper;
import com.besysoft.ejercitacion.excepciones.ExistException;
import com.besysoft.ejercitacion.excepciones.ListaIncorrectaException;
import com.besysoft.ejercitacion.servicios.interfaces.IGeneroService;
import com.besysoft.ejercitacion.dominio.Genero;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.*;

@RestController
@RequestMapping("/generos")
@Api(value="Genero Controlador", tags="Acciones para la entidad Genero")
public class ControladoraGenero {

    private Logger logger=LoggerFactory.getLogger(ControladoraGenero.class);
    private final IGeneroService genService;
    private final IGeneroMapper genMap;
    public Map<String,Object> mensajeBody= new HashMap<>();

    public ControladoraGenero(IGeneroService genService, IGeneroMapper genMap) {
        this.genService = genService;
        this.genMap = genMap;
    }

    private ResponseEntity<?> successResponse(List<?> lista){
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",lista);
        return ResponseEntity.ok(mensajeBody);
    }

    @GetMapping
    @ApiOperation(value="Consulta todos los generos existentes")
    public ResponseEntity<?>  verGeneros(){
        List<GeneroRespDTO> genRespDto=this.genMap.mapListToDto(this.genService.verGeneros());
        return this.successResponse(genRespDto);
    }

    @PostMapping
    @ApiOperation(value="Permite la cración de un genero")
    public ResponseEntity<?> altaGenero(@Valid @RequestBody GeneroReqDTO generoReq){
        Genero genero=genMap.mapToEntity(generoReq);
        logger.info("genero a crear: "+genero);
        Genero generoNuevo=this.genService.altaGenero(genero);
        GeneroRespDTO genDto=this.genMap.mapToDto(generoNuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(genDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value="Permite la edición de un genero")
    public ResponseEntity<?> modiGenero(@Valid @RequestBody GeneroReqDTO generoReq,
                                         @PathVariable int id){
        Genero genero=genMap.mapToEntity(generoReq);
        logger.info("genero a modificar: "+genero);
        Genero generoMod=this.genService.modiGenero(genero,id);
        GeneroRespDTO genDto=this.genMap.mapToDto(generoMod);

        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",genDto);
        return ResponseEntity.ok(mensajeBody);
    }



}
