package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.dto.PeliculaReqDTO;
import com.besysoft.ejercitacion.dto.PeliculaRespDTO;
import com.besysoft.ejercitacion.dto.mapper.IPeliculaMapper;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import com.besysoft.ejercitacion.servicios.interfaces.IPersonajeService;
import com.besysoft.ejercitacion.dominio.Pelicula;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/peliculas")
public class ControladoraPelicula {

    private Logger logger= LoggerFactory.getLogger(ControladoraPelicula.class);
    private final IPeliculaService peliService;
    private final IPersonajeService persoService;
    private final IPeliculaMapper peliMap;

    public ControladoraPelicula(IPeliculaService peliService, IPersonajeService persoService, IPeliculaMapper peliMap) {
        this.peliService = peliService;
        this.persoService = persoService;
        this.peliMap = peliMap;
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
        List< PeliculaRespDTO> pelisDto=this.peliMap.mapListToDto(this.peliService.verPelis());
        return this.successResponse(pelisDto);
    }

    @GetMapping("/{titulo}")
    public ResponseEntity<?> buscarPeliByTitulo(@PathVariable String titulo){
        List< PeliculaRespDTO> pelisDto=this.peliMap.mapListToDto(this.peliService.buscarPeliByTitulo(titulo));
        return this.successResponse(pelisDto);
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<?> buscarPeliByGenero(@PathVariable String genero){
        List< PeliculaRespDTO> pelisDto=this.peliMap.mapListToDto(this.peliService.buscarPeliByGenero(genero));
        return this.successResponse(pelisDto);
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
            List< PeliculaRespDTO> pelisDto=this.peliMap.mapListToDto(this.peliService.buscarPeliByFechas(desde,hasta));
            return this.successResponse(pelisDto);
        }
    }

    @GetMapping("/calificacion")
    public ResponseEntity<?> buscarPeliCalificacion(@RequestParam int desde,
                                           @RequestParam int hasta){
        if(desde>hasta || desde<1 || hasta>5){
            return this.notSuccessResponse("Rango no válido",0);
        }
        List< PeliculaRespDTO> pelisDto=this.peliMap.mapListToDto(this.peliService.buscarPeliByCal(desde,hasta));
        return this.successResponse(pelisDto);
    }
    @PostMapping
    public ResponseEntity<?> altaPelicula(@RequestBody PeliculaReqDTO peliDto){
        Pelicula peli=this.peliMap.mapToEntity(peliDto);
        logger.info("Pelicula a crear: "+peli);
        Pelicula pelicu=this.peliService.porSiListaPersoNull(peli);
        if(this.peliService.existeTitulo(peli)){
            return this.notSuccessResponse("Ya existe una pelicula ese nombre", 0);
        }
        if(!this.persoService.sonPersoCorrectos(pelicu.getListaPersonajes())){
            return this.notSuccessResponse("Algun personaje ingresado no existe",0);
        }
        Pelicula pelicula=this.peliService.altaPeli(pelicu);
        PeliculaRespDTO peliRespDto=this.peliMap.mapToDto(pelicula);
        return ResponseEntity.status(HttpStatus.CREATED).body(peliRespDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modiPelicula(@RequestBody PeliculaReqDTO peliDto,
                                            @PathVariable int id){
        Pelicula peli=this.peliMap.mapToEntity(peliDto);
        logger.info("Pelicula a modificar: "+peli);
        Pelicula pelicu=this.peliService.porSiListaPersoNull(peli);
        if(!this.peliService.existePeli(id)) {
            return this.notSuccessResponse("La pelicula con id %d ingresado no existe", id);
        }
        if(this.peliService.existeTituloConOtroId(peli, id)){
            return this.notSuccessResponse("Ya existe una pelicula con ese nombre", 0);
        }
        if(!persoService.sonPersoCorrectos(pelicu.getListaPersonajes())){
            return this.notSuccessResponse("Algun personaje ingresado no existe",0);
        }
        Pelicula peliM=this.peliService.modiPeli(pelicu,id);
        PeliculaRespDTO peliRespDto=this.peliMap.mapToDto(peliM);
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",peliRespDto);
        return ResponseEntity.ok(mensajeBody);
    }

}
