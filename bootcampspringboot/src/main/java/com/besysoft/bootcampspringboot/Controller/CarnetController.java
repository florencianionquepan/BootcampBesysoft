package com.besysoft.bootcampspringboot.Controller;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Carnet;
import com.besysoft.bootcampspringboot.negocio.dto.CarnetDto;
import com.besysoft.bootcampspringboot.negocio.mapper.mapstruct.CarnetMapper;
import com.besysoft.bootcampspringboot.servicios.interfaz.CarnetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carnets")
//@AllArgsConstructor //en vez del constructor uso lombok
public class CarnetController {
    private final CarnetService service;
    private final CarnetMapper mapper;

     public CarnetController(CarnetService service, CarnetMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public Carnet altaCarnet(@RequestBody CarnetDto carnetDto){
        Carnet carnet=this.mapper.mapToEntidad(carnetDto);
        return this.service.altaCarnet(carnet);
    }

    @GetMapping //este tambien deberia ir con mapper
    public Iterable<Carnet> verTodos(){
        return this.service.buscarTodos();
    }
}
