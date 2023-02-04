package com.besysoft.bootcampspringboot.servicios.implementacion;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Socio;
import com.besysoft.bootcampspringboot.repositorio.SocioRepository;
import com.besysoft.bootcampspringboot.servicios.interfaz.SocioService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SocioServiceImple implements SocioService {
    private final SocioRepository repo;

    public SocioServiceImple(SocioRepository repo) {
        this.repo = repo;
    }

    @Override
    public Socio altaSocio(Socio socio) {
        Optional <Socio> oSocio= this.repo.findByName(socio.getNombre());
        if(oSocio.isPresent()){
            throw new RuntimeException("socio existente");
        }
        return this.repo.save(socio);
    }

    @Override
    public Optional<Socio> buscarPorId(Long id) {
        return this.repo.findById(id);
    }

    @Override
    public Iterable<Socio> buscarTodos() {
        return this.repo.findAll();
    }
}
