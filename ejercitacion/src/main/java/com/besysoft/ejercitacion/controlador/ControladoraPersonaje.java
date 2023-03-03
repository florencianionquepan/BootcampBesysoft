package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.dto.PersonajeReqDTO;
import com.besysoft.ejercitacion.dto.PersonajeRespDTO;
import com.besysoft.ejercitacion.dto.mapper.IPersonajeMapper;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import com.besysoft.ejercitacion.servicios.interfaces.IPersonajeService;
import com.besysoft.ejercitacion.dominio.Personaje;
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
@RequestMapping("/personajes")
@Api(value="Personaje Controlador", tags="Acciones para la entidad Personaje")
public class ControladoraPersonaje {

    private Logger logger= LoggerFactory.getLogger(ControladoraPersonaje.class);
    private final IPersonajeService persoService;
    private final IPersonajeMapper persoMap;

    public ControladoraPersonaje(IPersonajeService service, IPersonajeMapper persoMap){
        this.persoService = service;
        this.persoMap = persoMap;
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
    @ApiOperation(value="Consulta todos los personajes existentes")
    public ResponseEntity<?> verPerso(){
        return this.successResponse(this.persoMap.mapListToDto(this.persoService.verPerso()));
    }

    @GetMapping("/{nombre}")
    @ApiOperation(value="Consulta los personajes existentes según su nombre")
    public ResponseEntity<?> buscarPersoByNombre(@PathVariable String nombre){
        return this.successResponse(this.persoMap.mapListToDto(this.persoService.buscarPersoByNombre(nombre)));
    }

    @GetMapping("/edad/{edad}")
    @ApiOperation(value="Consulta todos los personajes existentes según su edad")
    public ResponseEntity<?> buscarPersoByEdad(@PathVariable int edad){
        return this.successResponse(this.persoMap.mapListToDto(this.persoService.buscarPersoByEdad(edad)));
    }

    @GetMapping("/edad")
    @ApiOperation(value="Consulta todos los personajes existentes entre determinadas edades")
    public ResponseEntity<?> buscarPersoRangoEdad(@RequestParam int desde,
                                                 @RequestParam int hasta){
        if(desde>hasta){
            return this.notSuccessResponse("Rango de edad no válido",0);
        }
        return this.successResponse(this.persoMap.mapListToDto(this.persoService.buscarPersoRangoEdad(desde,hasta)));
    }

    @PostMapping
    @ApiOperation(value="Permite la creación de un personaje")
    public ResponseEntity<?> altaPersonaje(@Valid @RequestBody PersonajeReqDTO persoDto){
        Personaje perso=this.persoMap.mapToEntity(persoDto);
        logger.info("Personaje entidad a crear: " + perso);
        Personaje personaje=this.persoService.altaPersonaje(perso);
        PersonajeRespDTO persoRespDto=this.persoMap.mapToDto(personaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(persoRespDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value="Permite la edición de un personaje")
    public ResponseEntity<?> modiPerso(@Valid @RequestBody PersonajeReqDTO persoDto,
                                       @PathVariable int id){
        Personaje perso=this.persoMap.mapToEntity(persoDto);
        logger.info("Personaje entidad a modificar: " + perso);
        Personaje personaje=this.persoService.modiPersonaje(perso,id);
        PersonajeRespDTO persoRespDto=this.persoMap.mapToDto(personaje);
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",persoRespDto);
        return ResponseEntity.ok(mensajeBody);
    }


}
