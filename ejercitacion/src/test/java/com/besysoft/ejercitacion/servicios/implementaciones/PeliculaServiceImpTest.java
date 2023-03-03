package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.repositorios.database.GeneroRepository;
import com.besysoft.ejercitacion.repositorios.database.PeliculaRepository;
import com.besysoft.ejercitacion.repositorios.database.PersonajeRepository;
import com.besysoft.ejercitacion.utilidades.TestDatos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class PeliculaServiceImpTest {

    private Logger logger= LoggerFactory.getLogger(PeliculaServiceImpTest.class);
    private PeliculaServiceImp peliService;
    private PeliculaRepository peliRepo;
    private PersonajeRepository persoRepo;
    private GeneroRepository genRepo;

    @BeforeEach
    void setUp() {
        peliRepo=mock(PeliculaRepository.class);
        persoRepo=mock(PersonajeRepository.class);
        genRepo=mock(GeneroRepository.class);
        peliService=new PeliculaServiceImp(peliRepo,genRepo,persoRepo);
    }

    @Test
    void verPelis() {
        //GIVEN
        TestDatos test=new TestDatos();
        test.generarDatos();
        when(peliRepo.findAll()).thenReturn(TestDatos.getListaPelis());
        //WHEN
        List<Pelicula> peliculas=peliService.verPelis();
        //THEN
        assertThat(peliculas).isEqualTo(TestDatos.getListaPelis());
        verify(peliRepo).findAll();
    }

    @Test
    void buscarPeliByTitulo() {
        //GIVEN
        String titulo="Coco";
        when(peliRepo.findByTitle(titulo))
                .thenReturn(Optional.of(TestDatos.getPeliculaCoco()));
        //WHEN
        List<Pelicula> listaEsperada=peliService.buscarPeliByTitulo(titulo);
        //THEN
        assertThat(listaEsperada.get(0))
                .isEqualTo(Optional.of(TestDatos.getPeliculaCoco()).get());
        verify(peliRepo).findByTitle(titulo);
    }

    @Test
    void buscarPeliByGenero() {
        String genero="Terror";
        //le añado al objeto genero terror una pelicula:
        TestDatos.getGeneroTerror().getListaPelis()
                .add(TestDatos.getPeliculaUp());
        when(genRepo.findByName(genero))
                .thenReturn(Optional.of(TestDatos.getGeneroTerror()));
        //WHEN
        List<Pelicula> listaEsperadaConUp=peliService.buscarPeliByGenero(genero);
        //THEN
        assertThat(listaEsperadaConUp)
                .isEqualTo(Optional.of(TestDatos.getGeneroTerror()).get().getListaPelis());
        verify(genRepo).findByName(genero);
    }

    @Test
    void buscarPeliByFechas() {
        //supongo que va a buscar y no existen peliculas entre esas fechas
        LocalDate fechaIni=LocalDate.of(2005,1,01);
        LocalDate fechaFin=LocalDate.of(2000,9,01);
        when(peliRepo.findBetweenDates(fechaIni,fechaFin))
                .thenReturn(new ArrayList<Pelicula>());

        List<Pelicula> listaEsperada=peliService.buscarPeliByFechas(fechaIni,fechaFin);

        assertThat(listaEsperada.size()).isEqualTo(0);
        verify(peliRepo).findBetweenDates(fechaIni,fechaFin);
    }

    @Test
    void buscarPeliByCal() {
        int desde=1;
        int hasta=3;
        when(peliRepo.findBetweenCalif(desde,hasta))
                .thenReturn(new ArrayList<Pelicula>(
                        Arrays.asList(TestDatos.getPeliculaUp())
                ));

        List<Pelicula> listaEsp=peliService.buscarPeliByCal(desde,hasta);

        assertThat(listaEsp.get(0)).isEqualTo(TestDatos.getPeliculaUp());
        verify(peliRepo).findBetweenCalif(desde,hasta);
    }

    @Test
    void altaPeli() {
        Pelicula peli=TestDatos.getPeliculaUp();
        when(peliRepo.save(any())).thenReturn(peli);

        peliService.altaPeli(peli);

        ArgumentCaptor<Pelicula> peliArgCaptor=ArgumentCaptor.forClass(Pelicula.class);
        verify(peliRepo).save(peliArgCaptor.capture());

        assertThat(peliArgCaptor.getValue()).isEqualTo(peli);
    }

    @Test
    void altaPeliErrorUno() {
        Pelicula peli=TestDatos.getPeliculaUp();
        peli.setListaPersonajes(new ArrayList<Personaje>(
                Arrays.asList(TestDatos.getPersonajeCarl())
        ));
        when(persoRepo.findById(peli.getListaPersonajes().get(0).getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(()->peliService.altaPeli(peli))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Algun personaje ingresado no existe o no es correcto");
    }

    @Test
    void altaPeliErrorDos() {
        Pelicula peli=TestDatos.getPeliculaUp();
        Pelicula peliMismoTitulo=mock(Pelicula.class);
        when(peliRepo.save(any())).thenReturn(peliMismoTitulo);
        when(peliRepo.findByTitle(any()))
                .thenReturn(Optional.of(peliMismoTitulo));

        assertThatThrownBy(()->peliService.altaPeli(peli))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(String.format("La pelicula %s ya existe",peli.getTitulo()));
    }

    @Test
    void modiPeli() {
        //GIVEN
        Pelicula peli=TestDatos.getPeliculaUp();
        given(peliRepo.save(peli)).willReturn(peli);
        Pelicula peliMod=peli;
        peliMod.setTitulo("nuevo");
        //Este contexto se agrega para mockear las entradas a peliRepo
        //Cuando se ejecuta modiPeli()
        peliMod.setGenero(new Genero());
        when(peliRepo.existsById(peliMod.getId())).thenReturn(true);
        when(peliRepo.findById(any())).thenReturn(Optional.of(peli));
        //WHEN
        peliService.modiPeli(peliMod,peliMod.getId());
        //THEN
        assertThat(peli.getTitulo()).isEqualTo("nuevo");
        verify(peliRepo).save(peli);
    }

    @Test
    void modiPeliIdInexistente() {
        //GIVEN
        Pelicula peli=TestDatos.getPeliculaUp();
        given(peliRepo.existsById(any())).willReturn(false);
        //WHEN
        //THEN
        assertThatThrownBy(() -> peliService.modiPeli(peli, 3))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining((String.format("La pelicula con id %d no existe",3)));
    }

    @Test
    void retenerGenero() {
        Pelicula peli=TestDatos.getPeliculaUp();
        Pelicula peliGenero=peli;
        peliGenero.setGenero(TestDatos.getGeneroDrama());
        when(peliRepo.save(any())).thenReturn(peliGenero);
        when(peliRepo.findById(peli.getId()))
                .thenReturn(Optional.of(peliGenero));
        //WHEN
        peliService.retenerGenero(peli);
        //THEN
        assertThat(peli.getGenero()).isEqualTo(TestDatos.getGeneroDrama());
    }

    @Test
    void sonPelisCorrectasTest() {
        List<Pelicula> pelis=new ArrayList<Pelicula>(
                Arrays.asList(TestDatos.getPeliculaUp())
        );
        when(peliRepo.save(any())).thenReturn(pelis.get(0));
        when(peliRepo.findById(any())).thenReturn(Optional.of(pelis.get(0)));

        boolean resultadoEsperado= peliService.sonPelisCorrectas(pelis);
        assertTrue(resultadoEsperado);
    }

    @Test
    void addPeliPersosTest(){
        //yo asocio a la pelicula el personaje,
        //La funcion debe notificar al personaje que ahora tiene una pelicula en su lista!
        Pelicula peliCoco=TestDatos.getPeliculaCoco();
        Personaje persoPrueba=TestDatos.getPersonajeMiguel();
        List<Personaje> persos=new ArrayList<Personaje>(
                Arrays.asList(persoPrueba)
        );
        peliCoco.setListaPersonajes(persos);
        when(persoRepo.save(any())).thenReturn(persoPrueba);
        when(persoRepo.findById(any())).thenReturn(Optional.of(persoPrueba));
        logger.info("Peliculas de perso: "+persoPrueba.getListaPeliculas());

        peliService.addPeliPersos(peliCoco);

        logger.info("Peliculas de perso: "+persoPrueba.getListaPeliculas());
        boolean result=persoPrueba.getListaPeliculas().contains(peliCoco);
        assertTrue(result);
    }

    @Test
    void removePeliPersosTest(){
        Pelicula peliCoco=TestDatos.getPeliculaCoco();
        Personaje persoPrueba=TestDatos.getPersonajeMiguel();
        List<Personaje> persos=new ArrayList<Personaje>(
                Arrays.asList(persoPrueba)
        );
        peliCoco.setListaPersonajes(persos);
        List<Pelicula> pelicu=new ArrayList<Pelicula>(
                Arrays.asList(peliCoco)
        );
        persoPrueba.setListaPeliculas(pelicu);
        when(peliRepo.save(any())).thenReturn(peliCoco);
        when(peliRepo.findById(any())).thenReturn(Optional.of(peliCoco));
        when(persoRepo.save(any())).thenReturn(persoPrueba);
        when(persoRepo.findById(any())).thenReturn(Optional.of(persoPrueba));
        logger.info("Peliculas de perso: "+persoPrueba.getListaPeliculas());

        peliService.removePeliPersos(peliCoco.getId());

        logger.info("Peliculas de perso: "+persoPrueba.getListaPeliculas());
        boolean result=persoPrueba.getListaPeliculas().contains(peliCoco);
        assertFalse(result);
    }

}