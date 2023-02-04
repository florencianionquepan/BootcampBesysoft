package com.besysoft.bootcampspringboot.Controller;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Socio;
import com.besysoft.bootcampspringboot.servicios.interfaz.SocioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socios")
public class SocioController {
    private final SocioService service;

    public SocioController(SocioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> altaSocio(@RequestBody Socio socio){
        return ResponseEntity.ok(this.service.altaSocio(socio));
    }

    @GetMapping
    public ResponseEntity<?> verTodos(){
        return ResponseEntity.ok(this.service.buscarTodos());
    }
}
