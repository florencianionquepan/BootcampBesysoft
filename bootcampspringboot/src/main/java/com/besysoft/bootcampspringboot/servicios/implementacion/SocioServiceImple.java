package com.besysoft.bootcampspringboot.servicios.implementacion;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Socio;
import com.besysoft.bootcampspringboot.excepciones.SocioExistException;
import com.besysoft.bootcampspringboot.repositorio.SocioRepository;
import com.besysoft.bootcampspringboot.servicios.interfaz.SocioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SocioServiceImple implements SocioService {
    private final SocioRepository repo;

    public SocioServiceImple(SocioRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = false)
    public Socio altaSocio(Socio socio) {
        Optional <Socio> oSocio= this.repo.buscarPorNombre(socio.getNombre());
        if(oSocio.isPresent()){
            throw new SocioExistException(String.format("El socio %s ya existe", socio.getNombre()),
            new RuntimeException("Causa original")
            );
        }
        return this.repo.save(socio);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Socio> buscarPorId(Long id) {
        return this.repo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Socio> buscarTodos() {
        return this.repo.findAll();
    }
}
