package com.besysoft.ejercitacion.repositorios.database;

import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.utilidades.TestDatos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class PersonajeRepositoryTest {

    @Autowired
    private PersonajeRepository repo;

    @BeforeEach
    void setUp() {
        repo.save(TestDatos.getPersonajeMiguel());
        repo.save(TestDatos.getPersonajeCarl());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByName() {
        //GIVEN
        String name="Carl";

        //WHEN
        List<Personaje> listaPerso= repo.findByName(TestDatos.getPersonajeCarl().getNombre());

        //THEN
        assertThat(listaPerso.size()).isEqualTo(1);
        assertThat(listaPerso.get(0).getNombre()).isEqualTo(name);

    }

    @Test
    void findByAge() {
        //GIVEN
        int edad=11;

        //WHEN
        List<Personaje> listaPerso=repo.findByAge(TestDatos.getPersonajeMiguel().getEdad());

        //THEN
        assertThat(listaPerso.size()).isEqualTo(1);
        assertThat(listaPerso.get(0).getEdad()).isEqualTo(edad);
    }

    @Test
    void findBetweenAges() {
        //GIVEN
        List<Personaje> listaPeque=new ArrayList<Personaje>(
                Arrays.asList(TestDatos.getPersonajeMiguel())
        );

        //WHEN
        List<Personaje> listaPerso=repo.findBetweenAges(TestDatos.getPersonajeMiguel().getEdad()
                                                        , TestDatos.getPersonajeCarl().getEdad()-1);

        //THEN
        assertThat(listaPerso.size()).isEqualTo(1);
        assertThat(listaPerso).isEqualTo(listaPeque);

    }
}