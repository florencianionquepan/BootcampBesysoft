package com.besysoft.bootcampspringboot.Controller;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Socio;
import com.besysoft.bootcampspringboot.negocio.dto.SocioDto;
import com.besysoft.bootcampspringboot.negocio.mapper.SocioMapper;
import com.besysoft.bootcampspringboot.servicios.interfaz.SocioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/socios")
public class SocioController {

    private Logger logger= LoggerFactory.getLogger(SocioController.class);
    private final SocioService service;

    public SocioController(SocioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> altaSocio(@Valid @RequestBody SocioDto socio, BindingResult result){
        /*if(!esNombreValido(socio.getNombre())){
            throw new RuntimeException("Nombre Invalido");
        }*/
        Map<String,Object> validaciones= new HashMap<>();
        if(result.hasErrors()){
            logger.info("Ocurrio una validacion");
            //log.info("Ocurrio una validacion ");
            result.getFieldErrors()
                    .forEach(error->{
                        logger.info("Campo: "+error.getField()+" validacion: "+error.getDefaultMessage());
                        validaciones.put(error.getField(), error.getDefaultMessage());
                    });
            return ResponseEntity.badRequest().body(validaciones);
        }
        Socio entity= SocioMapper.mapToEntity(socio);
        return ResponseEntity.ok(this.service.altaSocio(entity));
    }

    @GetMapping
    public ResponseEntity<?> verTodos(){
        List<Socio> socios= (List<Socio>) this.service.buscarTodos();
        List<SocioDto> listaDto=socios.stream()
                .map(s->SocioMapper.mapToDto(s))
                .collect(Collectors.toList());
        //Esto de recibir una lista y mapearla podria ponetlo en SocioMapper
        return ResponseEntity.ok(listaDto);
    }

    private boolean esNombreValido(String nombre){
        return nombre.matches("^[A-Za-z]+$");
    }
}
