package com.besysoft.bootcampspringboot.servicios.implementacion;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Carnet;
import com.besysoft.bootcampspringboot.repositorio.CarnetRepository;
import com.besysoft.bootcampspringboot.servicios.interfaz.CarnetService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CarnetServiceImple implements CarnetService {
    private final CarnetRepository repo;

    public CarnetServiceImple(CarnetRepository repo) {
        this.repo = repo;
    }

    @Override
    public Carnet altaCarnet(Carnet carnet) {
        return this.repo.save(carnet);
    }

    @Override
    public Optional<Carnet> buscarPorId(Long id) {
        return this.repo.findById(id);
    }

    @Override
    public Iterable<Carnet> buscarTodos() {
        return this.repo.findAll();
    }
}
