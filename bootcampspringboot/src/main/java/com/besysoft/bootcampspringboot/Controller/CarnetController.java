package com.besysoft.bootcampspringboot.Controller;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Carnet;
import com.besysoft.bootcampspringboot.servicios.interfaz.CarnetService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carnets")
public class CarnetController {
    private final CarnetService service;

    public CarnetController(CarnetService service) {
        this.service = service;
    }

    @PostMapping
    public Carnet altaCarnet(@RequestBody Carnet carnet){
        return this.service.altaCarnet(carnet);
    }

    @GetMapping
    public Iterable<Carnet> verTodos(){
        return this.service.buscarTodos();
    }
}
