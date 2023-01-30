package com.besysoft.bootcampspringboot.repositorio;

import dominio.Persona;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonaRepositoryMemory implements PersonaRepository{
    private List<Persona> listaPersonas;
    public PersonaRepositoryMemory(){
        this.listaPersonas = new ArrayList<Persona>(
                Arrays.asList(
                        new Persona(1L, "Florencia", "Nonqueapn", "361920"),
                        new Persona(2L, "Marcos", "Papapaleo", "9242500")

                )
        );
    }
    @Override
    public Persona altaPersona(Persona persona) {
        persona.setId((long) (this.listaPersonas.size() + 1));
        this.listaPersonas.add(persona);
        return persona;
    }

    @Override
    public Optional<Persona> buscarPorDni(String dni) {
        return this.listaPersonas.stream().filter(per->per.getDni().equals(dni)).findAny();
    }

    @Override
    public Optional<Persona> buscarPorId(Long id) {
        return this.listaPersonas.stream().filter(per->per.getId().equals(id)).findAny();
    }

    @Override
    public Iterable<Persona> obtenerTodos() {
        return this.listaPersonas;
    }
}
