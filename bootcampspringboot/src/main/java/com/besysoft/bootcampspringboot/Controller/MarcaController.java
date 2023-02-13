package com.besysoft.bootcampspringboot.Controller;

import com.besysoft.bootcampspringboot.entidades.manyToOne.Marca;
import com.besysoft.bootcampspringboot.servicios.interfaz.MarcaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    private final MarcaService service;

    public MarcaController(MarcaService service) {
        this.service = service;
    }

    @PostMapping
    public Marca altaMarca(@RequestBody Marca marca){
        return this.service.altaMarca(marca);
    }

    @GetMapping
    public Page<Marca> paginado(Pageable pageable){
        return this.service.paginado(pageable);
    }
}
