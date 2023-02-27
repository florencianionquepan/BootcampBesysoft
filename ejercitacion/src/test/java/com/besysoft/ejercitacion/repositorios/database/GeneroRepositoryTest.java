package com.besysoft.ejercitacion.repositorios.database;

import com.besysoft.ejercitacion.datos.DatosDummy;
import com.besysoft.ejercitacion.dominio.Genero;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GeneroRepositoryTest {

    @Autowired
    private GeneroRepository repo;

    @BeforeEach
    void setUp() {
        repo.save(DatosDummy.getGeneroTerror());
        repo.save(DatosDummy.getGeneroDrama());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByName() {
        //GIVEN
        String test="Terror";

        //WHEN
        Optional<Genero> oGenero = repo.findByName(DatosDummy.getGeneroTerror().getNombre());

        //THEN
        assertThat(oGenero.isPresent()).isTrue();
        assertThat(oGenero.get().getNombre().equals(test));

    }
}