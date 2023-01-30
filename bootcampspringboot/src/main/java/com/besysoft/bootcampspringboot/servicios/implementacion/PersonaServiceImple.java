/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.besysoft.bootcampspringboot.servicios.implementacion;

import com.besysoft.bootcampspringboot.servicios.interfaz.PersonaService;
import com.besysoft.bootcampspringboot.repositorio.PersonaRepository;
import dominio.Persona;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
//@Primary
@ConditionalOnProperty(prefix = "app", name="type-bean",havingValue = "memory")
public class PersonaServiceImple implements PersonaService {
    private final PersonaRepository repository;
    public PersonaServiceImple(PersonaRepository repository) {
        this.repository=repository;
    }


    @Override
    public Persona altaPersona(Persona persona) {
    Optional<Persona> oPerso=this.repository.buscarPorDni(persona.getDni());
        if(oPerso.isPresent()) {
            return null;
        }
        return this.repository.altaPersona(persona);
    }

    @Override
    public Optional<Persona> buscarPorDni(String dni) {
        return this.repository.buscarPorDni(dni);
    }

    @Override
    public Iterable<Persona> obtenerTodos() {
        return this.repository.obtenerTodos();
    }
}
