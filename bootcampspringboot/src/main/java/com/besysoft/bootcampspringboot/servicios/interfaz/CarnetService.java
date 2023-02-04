package com.besysoft.bootcampspringboot.servicios.interfaz;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Carnet;

import java.util.Optional;

public interface CarnetService {
    Carnet altaCarnet(Carnet carnet);
    Optional<Carnet> buscarPorId(Long id);
    Iterable <Carnet> buscarTodos();
}
