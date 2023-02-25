package com.besysoft.bootcampspringboot.servicios.implementacion;

import com.besysoft.bootcampspringboot.datos.DatosDummy;
import com.besysoft.bootcampspringboot.entidades.oneToOne.Socio;
import com.besysoft.bootcampspringboot.repositorio.SocioRepository;
import com.besysoft.bootcampspringboot.servicios.interfaz.SocioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

//@SpringBootTest
class SocioServiceImpleTest {

    //@Mock //esto no se va a testear
    //@MockBean
    private SocioRepository repository;

    //@Autowired
    private SocioService service;

    @BeforeEach
    void setUp() {
        repository=mock(SocioRepository.class);
        service=new SocioServiceImple(repository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void altaSocio() {
        //GIVEN, estoy simulando un insert, y lo simulo retornando eso
        //como se crea una instancia nueva del objeto, si lo guardo asi da error el test
        /*when(repository.save(any()))
                .thenReturn(DatosDummy.getSocioTres());*/
        Socio socio= DatosDummy.getSocioTres();

        //WHEN
        service.altaSocio(socio);

        //THEN
        //evaluar que el argumento que le estemos pasando realmente corresponda a la clase Socio
        ArgumentCaptor<Socio> socioArgumentCaptor = ArgumentCaptor.forClass(Socio.class);

        //verificando que se llame al metodo save
        verify(repository).save(socioArgumentCaptor.capture());
        Socio socioCaptor= socioArgumentCaptor.getValue();

        //el que viene por argumento que sea realmente socioTres
        assertThat(socioCaptor).isEqualTo(socio);
    }

    @Test
    @DisplayName("[Service]= ALta de socio existente")
    void altaSocioWithError() {
        //GIVEN
        Socio socio= DatosDummy.getSocioTres();

        given(repository.buscarPorNombre(socio.getNombre()))
                .willReturn(Optional.of(socio));

        //WHEN


        //THEN
        assertThatThrownBy(()->service.altaSocio(socio))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(String.format("El socio %s ya existe", socio.getNombre()));
    }

    @Test
    void buscarPorId() {

    }

    @Test
    void buscarTodos() {
        //Given
        //Con el when estamos mockeando la llamada al repo
        when(repository.findAll())
                .thenReturn(DatosDummy.getSocios());
        //when
        List<Socio> socios = (List<Socio>) service.buscarTodos();

        //then
        assertThat(socios.size())
                .isEqualTo(2);

        //verificar que realmente estemos llamando al metodos findAll de nuestro servicio
        verify(repository,times(1)).findAll();
    }
}