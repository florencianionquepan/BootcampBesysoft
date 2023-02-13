package com.besysoft.bootcampspringboot.servicios.implementacion;

import com.besysoft.bootcampspringboot.entidades.manyToOne.Marca;
import com.besysoft.bootcampspringboot.repositorio.MarcaRepository;
import com.besysoft.bootcampspringboot.servicios.interfaz.MarcaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MarcaServiceImple implements MarcaService {

    private final MarcaRepository repo;

    public MarcaServiceImple(MarcaRepository repo) {
        this.repo = repo;
    }

    public Marca altaMarca(Marca marca){
        return this.repo.save(marca);
    }

    public Page<Marca> paginado(Pageable pageable){
        return this.repo.findAll(pageable);
    }
}
