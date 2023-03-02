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

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/peliculas")
public class ControladoraPelicula {

    private Logger logger= LoggerFactory.getLogger(ControladoraPelicula.class);
    private final IPeliculaService peliService;
    private final IPeliculaMapper peliMap;

    public ControladoraPelicula(IPeliculaService peliService, IPeliculaMapper peliMap) {
        this.peliService = peliService;
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
    public ResponseEntity<?> altaPelicula(@Valid @RequestBody PeliculaReqDTO peliDto){
        Pelicula peli=this.peliMap.mapToEntity(peliDto);
        logger.info("Pelicula a crear: "+peli);
        Pelicula pelicula=this.peliService.altaPeli(peli);
        //El mensaje de error de BadRequest especifico vendrá luego por excepcion
        // y será manejado por ApiControllerAdvice
        if(pelicula==null){
            return this.notSuccessResponse("No se puede crear la pelicula", 0);
        }
        PeliculaRespDTO peliRespDto=this.peliMap.mapToDto(pelicula);
        return ResponseEntity.status(HttpStatus.CREATED).body(peliRespDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modiPelicula(@Valid @RequestBody PeliculaReqDTO peliDto,
                                            @PathVariable int id){
        Pelicula peli=this.peliMap.mapToEntity(peliDto);
        logger.info("Pelicula a modificar: "+peli);
        Pelicula peliM=this.peliService.modiPeli(peli,id);
        if(peliM==null){
            return this.notSuccessResponse("La pelicula no pudo ser modificada",0);
        }
        PeliculaRespDTO peliRespDto=this.peliMap.mapToDto(peliM);
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",peliRespDto);
        return ResponseEntity.ok(mensajeBody);
    }

}
