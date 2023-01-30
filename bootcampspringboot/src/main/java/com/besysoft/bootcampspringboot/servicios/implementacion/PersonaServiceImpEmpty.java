package com.besysoft.bootcampspringboot.servicios.implementacion;

import com.besysoft.bootcampspringboot.servicios.interfaz.PersonaService;
import dominio.Persona;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "app", name="type-bean",havingValue = "empty")
public class PersonaServiceImpEmpty implements PersonaService {
    @Override
    public Persona altaPersona(Persona persona) {
        return null;
    }

    @Override
    public Optional<Persona> buscarPorDni(String dni) {
        return Optional.empty();
    }

    @Override
    public Iterable<Persona> obtenerTodos() {
        return null;
    }
}
