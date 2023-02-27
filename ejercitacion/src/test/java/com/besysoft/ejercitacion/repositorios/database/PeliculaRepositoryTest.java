package com.besysoft.ejercitacion.repositorios.database;

import com.besysoft.ejercitacion.datos.DatosDummy;
import com.besysoft.ejercitacion.dominio.Pelicula;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PeliculaRepositoryTest {

    @Autowired
    private PeliculaRepository repo;

    @BeforeEach
    void setUp() {
        repo.save(DatosDummy.getPeliculaCoco());
        repo.save(DatosDummy.getPeliculaUp());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByTitle() {
        //GIVEN
        String titulo="Coco";

        //WHEN
        Optional<Pelicula> oPeli = repo.findByTitle(DatosDummy.getPeliculaCoco().getTitulo());

        //THEN
        assertThat(oPeli.isPresent())
                .isTrue();
        assertThat(oPeli.get().getTitulo()).isEqualTo(titulo);
    }

    @Test
    void findBetweenDates() {
        //GIVEN
        List<Pelicula> pelisDates=new ArrayList<>(
                List.of(
                        DatosDummy.getPeliculaCoco()
                )
        );

        //WHEN
        List<Pelicula> listaPelis= (List<Pelicula>) repo.findBetweenDates(LocalDate.of(2015,11,01)
                            ,LocalDate.of(2020,11,01));

        //THEN
        assertThat(listaPelis.size()).isEqualTo(1);
        assertThat(listaPelis).isEqualTo(pelisDates);

    }

    @Test
    void findBetweenCalif() {
        //GIVEN
        List<Pelicula> pelisCalif=new ArrayList<>(
                List.of(
                        DatosDummy.getPeliculaUp()
                )
        );

        //WHEN
        List<Pelicula> listaPelis = (List<Pelicula>) repo.findBetweenCalif(1, 2);

        //THEN
        assertThat(listaPelis.size()).isEqualTo(1);
        assertThat(listaPelis).isEqualTo(pelisCalif);
    }
}