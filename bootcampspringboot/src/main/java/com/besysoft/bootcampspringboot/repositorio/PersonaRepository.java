package com.besysoft.bootcampspringboot.repositorio;

import dominio.Persona;

import java.util.Optional;

//Responsabilidad de los repositorio es el accceso a datos independientes de donde se encuentren alojados
public interface PersonaRepository {
    Persona altaPersona(Persona persona);
    Optional<Persona> buscarPorDni(String dni);
    Optional<Persona> buscarPorId(Long id);
    Iterable<Persona> obtenerTodos();
}
