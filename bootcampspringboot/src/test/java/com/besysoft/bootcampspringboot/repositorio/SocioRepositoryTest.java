package com.besysoft.bootcampspringboot.repositorio;

import com.besysoft.bootcampspringboot.datos.DatosDummy;
import com.besysoft.bootcampspringboot.entidades.oneToOne.Socio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SocioRepositoryTest {

    @Autowired
    private SocioRepository repository;

    @Test
    void buscarPorNombre() {

        //GIVEN
        String test="Socio uno";
        repository.save(DatosDummy.getSocioUno());
        repository.save(DatosDummy.getSocioDos());
        repository.save(DatosDummy.getSocioTres());

        //WHEN
        Optional<Socio> oSocio = repository.buscarPorNombre(DatosDummy.getSocioUno().getNombre());

        //THEN
        assertThat(oSocio.isPresent())
                .isTrue();
        assertThat(oSocio.get().getNombre())
                .isEqualTo(test);

    }
}