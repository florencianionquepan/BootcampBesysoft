package com.besysoft.bootcampspringboot.servicios.interfaz;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Socio;

import java.util.Optional;

public interface SocioService {
    Socio altaSocio(Socio socio);
    Optional<Socio> buscarPorId(Long id);
    Iterable<Socio> buscarTodos();
}
