package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.repositorios.database.PeliculaRepository;
import com.besysoft.ejercitacion.repositorios.database.PersonajeRepository;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import com.besysoft.ejercitacion.utilidades.TestDatos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class PersonajeServiceImpTest {

    private PersonajeServiceImp persoService;
    private PersonajeRepository persoRepo;
    private IPeliculaService peliService;
    private PeliculaRepository peliRepo;
    private Logger logger= LoggerFactory.getLogger(PersonajeServiceImpTest.class);

    @BeforeEach
    void setUp() {
        persoRepo=mock(PersonajeRepository.class);
        peliService=mock(IPeliculaService.class);
        peliRepo=mock(PeliculaRepository.class);
        persoService=new PersonajeServiceImp(persoRepo,peliService,peliRepo);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void verPerso() {
        //GIVEN
        TestDatos test=new TestDatos();
        test.generarDatos();
        when(persoRepo.findAll())
                .thenReturn(TestDatos.getListaPerso());
        //WHEN
        List<Personaje> listaPerso= persoService.verPerso();
        //THEN
        assertThat(listaPerso.size()).isEqualTo(6);
        verify(persoRepo).findAll();
    }

    @Test
    void buscarPersoByNombre() {
        //GIVEN
        when(persoRepo.findByName("Carl"))
                .thenReturn(new ArrayList<Personaje>(
                        Arrays.asList(TestDatos.getPersonajeCarl())
                ));
        //WHEN
        List<Personaje> personajesEsperados= persoService
                .buscarPersoByNombre("Carl");
        //THEN
        assertThat(personajesEsperados.get(0)).isEqualTo(TestDatos.getPersonajeCarl());
        verify(persoRepo).findByName("Carl");
    }

    @Test
    void buscarPersoByEdad() {
        //GIVEN
        int edad=11;
        when(persoRepo.findByAge(edad))
                .thenReturn(new ArrayList<Personaje>(
                        Arrays.asList(TestDatos.getPersonajeMiguel())
                ));
        //WHEN
        List<Personaje> personajesEsperados= persoService
                .buscarPersoByEdad(edad);
        //THEN
        assertThat(personajesEsperados.size()).isEqualTo(1);
        assertThat(personajesEsperados.get(0)).isEqualTo(TestDatos.getPersonajeMiguel());
        verify(persoRepo).findByAge(TestDatos.getPersonajeMiguel().getEdad());
    }

    @Test
    void buscarPersoRangoEdad() {
        //GIVEN
        when(persoRepo.findBetweenAges(10,30))
                .thenReturn(new ArrayList<Personaje>(
                        Arrays.asList(TestDatos.getPersonajeMiguel())
                ));
        //WHEN
        List<Personaje> personajes= persoService
                .buscarPersoRangoEdad(10,30);
        //THEN
        assertThat(personajes.size()).isEqualTo(1);
        assertThat(personajes.get(0)).isEqualTo(TestDatos.getPersonajeMiguel());
        verify(persoRepo).findBetweenAges(10,30);
    }

    @Test
    void altaPersonaje() {
        Personaje per=TestDatos.getPersonajeCarl();
        //GIVEN
        when(persoRepo.save(any()))
                .thenReturn(per);
        when(peliService.sonPelisCorrectas(per.getListaPeliculas()))
                .thenReturn(true);
        //WHEN
        persoService.altaPersonaje(per);
        //THEN
        ArgumentCaptor<Personaje> persoArgumentCaptor= ArgumentCaptor.forClass(Personaje.class);
        verify(persoRepo).save(persoArgumentCaptor.capture());

        assertThat(persoArgumentCaptor.getValue()).isEqualTo(per);
    }

    @Test
    void altaPersonajeError(){
        Personaje per=TestDatos.getPersonajeCarl();
        //given
        when(peliService.sonPelisCorrectas(per.getListaPeliculas()))
                .thenReturn(false);
        //WHEN
        //THEN
        assertThatThrownBy(()->persoService.altaPersonaje(per))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Alguna pelicula ingresada no existe o no es correcta");
    }

    @Test
    void modiPersonaje() {
        Personaje per1=TestDatos.getPersonajeCarl();
        //GIVEN
        when(persoRepo.save(per1)).thenReturn(per1);
        when(persoRepo.findById(per1.getId())).thenReturn(Optional.of(per1));
        when(peliService.sonPelisCorrectas(per1.getListaPeliculas())).thenReturn(true);
        per1.setNombre("Carlos");
        //WHEN
        persoService.modiPersonaje(per1,per1.getId());
        //THEN
        assertThat(per1.getNombre()).isEqualTo("Carlos");
        verify(persoRepo).save(per1);
    }

    @Test
    void modiPersonajeErrorUno() {
        Personaje perso=TestDatos.getPersonajeCarl();
        //GIVEN
        when(persoRepo.save(any()))
                .thenReturn(perso);
        when(persoRepo.findById(any()))
                .thenReturn(Optional.of(perso));
        when(peliService.sonPelisCorrectas(perso.getListaPeliculas()))
                .thenReturn(false);
        //WHEN
        //THEN
        assertThatThrownBy(()->persoService.modiPersonaje(perso,3))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Alguna pelicula ingresada no existe o no es correcta");
    }

    @Test
    void addPersoPeliTest() {
        //GIVEN
        Personaje persoCarl = TestDatos.getPersonajeCarl();
        Pelicula peliPrueba=TestDatos.getPeliculaUp();
        List<Pelicula> listaPelis= new ArrayList<Pelicula>(
                Arrays.asList(peliPrueba)
        );
        persoCarl.setListaPeliculas(listaPelis);
        when(peliRepo.save(peliPrueba)).thenReturn(peliPrueba);
        when(peliRepo.findById(peliPrueba.getId()))
                .thenReturn(Optional.of(peliPrueba));
        //WHEN
        persoService.addPersoPelis(persoCarl);
        //THEN
        boolean resultado=peliPrueba.getListaPersonajes().contains(persoCarl);
        assertTrue(resultado);
    }

    @Test
    void removePersoPelisTest(){
        //given
        Personaje miguel=TestDatos.getPersonajeMiguel();
        miguel.setListaPeliculas(new ArrayList<Pelicula>(
                Arrays.asList(TestDatos.getPeliculaCoco())
        ));
        logger.info("Miguel "+ miguel + miguel.getListaPeliculas());
        Pelicula peliCoco=TestDatos.getPeliculaCoco();
        peliCoco.setListaPersonajes(new ArrayList<>(
                Arrays.asList(TestDatos.getPersonajeMiguel())
        ));
        logger.info("Peli coco"+ peliCoco + peliCoco.getListaPersonajes());
        when(persoRepo.save(miguel)).thenReturn(miguel);
        when(persoRepo.findById(miguel.getId())).thenReturn(Optional.of(miguel));
        when(peliRepo.save(peliCoco)).thenReturn(peliCoco);
        when(peliRepo.findById(peliCoco.getId())).thenReturn(Optional.of(peliCoco));
        //when
        persoService.removePersoPelis(miguel.getId());
        //then
        boolean result=peliCoco.getListaPersonajes().contains(miguel);
        assertFalse(result);
    }
}