package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.dto.PeliculaReqDTO;
import com.besysoft.ejercitacion.dto.mapper.IPeliculaMapper;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import com.besysoft.ejercitacion.utilidades.TestDatos;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ControladoraPelicula.class)
class ControladoraPeliculaTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IPeliculaMapper mapper;
    @MockBean
    private IPeliculaService service;
    private String url;
    private ObjectMapper objMapper;
    private Logger logger= LoggerFactory.getLogger(ControladoraPelicula.class);

    @BeforeEach
    void setUp() {
        this.url="/peliculas";
        this.objMapper=new ObjectMapper();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void verPelis() throws Exception {
        //GIVEN
        when(service.verPelis()).thenReturn(TestDatos.getListaPelis());
        //WHEN
        mockMvc.perform(get(this.url)
                .contentType(MediaType.APPLICATION_JSON))
        //THEN
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data").value(mapper.mapListToDto(TestDatos.getListaPelis())));
        logger.info(" "+jsonPath("$.data"));
    }


    @Test
    void buscarPeliByTitulo() throws Exception {
        //GIVEN
        List<Pelicula> peliculas=new ArrayList<Pelicula>(
                Arrays.asList(TestDatos.getPeliculaCoco())
        );
        when(service.buscarPeliByTitulo("Coco"))
                .thenReturn(peliculas);
        //WHEN
        String urlTitulo=this.url+"/Coco";
        mockMvc.perform(get(urlTitulo)
                        .contentType(MediaType.APPLICATION_JSON))

        //THEN
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void buscarPeliByGenero() throws Exception {
        List<Pelicula> pelisTerror=new ArrayList<Pelicula>(
                Arrays.asList(TestDatos.getPeliculaUp())
        );
        when(service.buscarPeliByGenero("Terror"))
                .thenReturn(pelisTerror);

        String urlGenero=this.url+"/genero/Terror";
        mockMvc.perform(get(urlGenero)
                .contentType(MediaType.APPLICATION_JSON))
        //THEN
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void buscarPeliFechasBadRequest() throws Exception {
        String desde="01012020";
        String hasta="01012008";
        String url=this.url+"/fechas";
        mockMvc.perform(get(url)
                .param("desde",desde)
                .param("hasta",hasta)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void buscarPeliCalificacionBadRequest() throws Exception {
        String desde="4";
        String hasta="2";
        String url=this.url+"/calificacion";
        mockMvc.perform(get(url)
                .param("desde",desde)
                .param("hasta",hasta)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void altaPelicula() throws Exception{
        PeliculaReqDTO peliReq=new PeliculaReqDTO();
        peliReq.setTitulo("Igorio");
        peliReq.setCalificacion(5);
        when(mapper.mapToEntity(peliReq))
                .thenReturn(new Pelicula());
        when(service.altaPeli(any()))
                .thenReturn(new Pelicula());
        //System.out.println(this.objMapper.writeValueAsString(peliReq));
        mockMvc.perform(post(this.url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objMapper.writeValueAsString(peliReq))
        ).andExpect(status().isCreated());
    }

    @Test
    void modiPelicula() throws Exception{
        PeliculaReqDTO peliReq=new PeliculaReqDTO();
        peliReq.setTitulo("Igorio");
        peliReq.setCalificacion(5);
        when(mapper.mapToEntity(peliReq))
                .thenReturn(new Pelicula());
        when(service.modiPeli(any(),anyInt()))
                .thenReturn(new Pelicula());
        //System.out.println(this.objMapper.writeValueAsString(peliReq));
        String urlModi=this.url+"/1";
        mockMvc.perform(put(urlModi)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objMapper.writeValueAsString(peliReq))
        ).andExpect(status().isOk());
    }
}