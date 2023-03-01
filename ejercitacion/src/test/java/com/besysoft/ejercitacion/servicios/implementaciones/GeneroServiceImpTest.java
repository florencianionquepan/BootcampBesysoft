package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.repositorios.database.GeneroRepository;
import com.besysoft.ejercitacion.repositorios.database.PeliculaRepository;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import com.besysoft.ejercitacion.utilidades.TestDatos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


class GeneroServiceImpTest {

    private GeneroServiceImp genService;
    private IPeliculaService peliService;
    private GeneroRepository genRepo;
    private PeliculaRepository peliRepo;

    @BeforeEach
    void setUp() {
        peliService=mock(IPeliculaService.class);
        genRepo=mock(GeneroRepository.class);
        peliRepo=mock(PeliculaRepository.class);
        genService=new GeneroServiceImp(genRepo,peliRepo,peliService);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void verGeneros() {
        //GIVEN
        TestDatos miTestDatos =new TestDatos();
        miTestDatos.generarDatos();
        when(genRepo.findAll())
                .thenReturn(TestDatos.getListaGeneros());
        //WHEN
        List<Genero> generos = genService.verGeneros();
        //THEN
        assertThat(generos.size()).isEqualTo(2);
        verify(genRepo).findAll();
    }

    @Test
    void altaGenero() {
        Genero genTerror= TestDatos.getGeneroTerror();
        //GIVEN
        when(genRepo.save(any()))
                .thenReturn(genTerror);
        when(peliService.sonPelisCorrectas(genTerror.getListaPelis()))
                .thenReturn(true);

        //WHEN
        genService.altaGenero(genTerror);
        //THEN
        //aca seteo el argumentCaptor y le digo que tiene qe ser de tipo genero
        ArgumentCaptor<Genero> generoArgumentCaptor= ArgumentCaptor.forClass(Genero.class);
        verify(genRepo).save(generoArgumentCaptor.capture());

        Genero genCaptor=generoArgumentCaptor.getValue();
        assertThat(genCaptor).isEqualTo(genTerror);
    }

    @Test
    void altaGeneroErrorUno() {
        Genero genTerror= TestDatos.getGeneroTerror();
        //GIVEN
        given(genRepo.findByName(genTerror.getNombre()))
                .willReturn(Optional.of(genTerror));
        //WHEN
        //THEN
        assertThat(genService.altaGenero(genTerror)).isEqualTo(null);
    }

    @Test
    void altaGeneroErrorDos() {
        Genero genTerror= TestDatos.getGeneroTerror();
        //GIVEN
        given(peliService.sonPelisCorrectas(genTerror.getListaPelis()))
                .willReturn(false);
        //WHEN
        //THEN
        assertThat(genService.altaGenero(genTerror)).isEqualTo(null);
    }

    @Test
    void modiGenero() {
        Genero genDrama= TestDatos.getGeneroDrama();
        //GIVEN
        when(genRepo.save(genDrama)).thenReturn(genDrama);
        when(genRepo.findById(genDrama.getId())).thenReturn(Optional.of(genDrama));
        Genero genModi=genDrama;
        genModi.setNombre("Infantil");
        when(peliService.sonPelisCorrectas(any())).thenReturn(true);
        when(genRepo.existsById(genModi.getId())).thenReturn(true);
        when(genRepo.findByName(genModi.getNombre())).thenReturn(Optional.empty());
        //WHEN
        genService.modiGenero(genModi,genModi.getId());
        //THEN
        assertThat(genDrama.getNombre()).isEqualTo("Infantil");
        verify(genRepo).save(genModi);
    }

    @Test
    void existeNombreConOtroIdTest() {
        Genero genDrama= TestDatos.getGeneroDrama();
        Genero genTerror= TestDatos.getGeneroTerror();
        //GIVEN
        Genero genModificar=new Genero();
        genModificar.setNombre("Terror");
        genModificar.setId(2);
        Genero genModificar2=new Genero();
        genModificar2.setNombre("Terror");
        genModificar2.setId(1);
        when(genRepo.findByName("Terror"))
                .thenReturn(Optional.of(genTerror));
        when(genRepo.findById(2))
                .thenReturn(Optional.of(genDrama));
        when(genRepo.findById(1))
                .thenReturn(Optional.of(genTerror));
        //WHEN
        //en este caso estoy queriendo modificar genDrama con el mismo nombre de terror.
        boolean res1= genService.existeNombreConOtroId(genModificar,2);
        //en este caso estoy modificando el genero terror (id=1);
        boolean res2=genService.existeNombreConOtroId(genModificar2,1);
        //THEN
        assertTrue(res1);
        assertFalse(res2);
    }

    @Test
    void modiGeneroErrorUno() {
        genService=spy(new GeneroServiceImp(genRepo,peliRepo,peliService));
        Genero genTerror= TestDatos.getGeneroTerror();
        genTerror.setNombre("Drama");
        //GIVEN
        doReturn(true).when(genService)
                .existeNombreConOtroId(genTerror,1);
        //WHEN
        //THEN
        assertThat(genService.modiGenero(genTerror,1)).isEqualTo(null);
    }

    @Test
    void modiGeneroErrorDos() {
        Genero genTerror= TestDatos.getGeneroTerror();
        //GIVEN
        given(peliService.sonPelisCorrectas(genTerror.getListaPelis()))
                .willReturn(false);
        //WHEN
        //THEN
        assertThat(genService.modiGenero(genTerror,2)).isEqualTo(null);
    }
}