package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.dto.PersonajeReqDTO;
import com.besysoft.ejercitacion.dto.PersonajeRespDTO;
import com.besysoft.ejercitacion.dto.mapper.IGeneroMapper;
import com.besysoft.ejercitacion.dto.mapper.IPersonajeMapper;
import com.besysoft.ejercitacion.servicios.interfaces.IGeneroService;
import com.besysoft.ejercitacion.servicios.interfaces.IPersonajeService;
import com.besysoft.ejercitacion.utilidades.TestDatos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControladoraPersonaje.class)
class ControladoraPersonajeTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IPersonajeMapper mapper;
    @MockBean
    private IPersonajeService service;
    private String url;
    private ObjectMapper objMapper;

    @BeforeEach
    void setUp() {
        this.url="/personajes";
        this.objMapper=new ObjectMapper();
    }

    @Test
    void verPerso() throws Exception {
        when(service.verPerso()).thenReturn(TestDatos.getListaPerso());

        mockMvc.perform(get(this.url)
                .contentType(MediaType.APPLICATION_JSON))

        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data").value(mapper.mapListToDto(TestDatos.getListaPerso())));
    }

    @Test
    void buscarPersoByNombre() throws Exception {
        List<Personaje> persos=new ArrayList<Personaje>(
                Arrays.asList(TestDatos.getPersonajeCarl())
        );
        when(service.buscarPersoByNombre(any())).thenReturn(persos);

        mockMvc.perform(get(this.url+"/Carl")
                        .contentType(MediaType.APPLICATION_JSON))

        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data").value(mapper.mapListToDto(persos)));
    }

    @Test
    void buscarPersoByEdad() throws Exception {
        List<Personaje> persos=new ArrayList<Personaje>(
                Arrays.asList(TestDatos.getPersonajeCarl())
        );
        when(service.buscarPersoByEdad(80)).thenReturn(persos);

        mockMvc.perform(get(this.url+"/edad/80")
                        .contentType(MediaType.APPLICATION_JSON))

        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data").value(mapper.mapListToDto(persos)));
    }

    @Test
    void buscarPersoRangoEdad() throws Exception {
        int desde=50;
        int hasta=8;
        String url=this.url+"/edad";
        mockMvc.perform(get(url)
                .param("desde", String.valueOf(desde))
                .param("hasta",String.valueOf(hasta))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void altaPersonaje() throws Exception {
        PersonajeReqDTO perReq=new PersonajeReqDTO();
        PersonajeRespDTO resp=new PersonajeRespDTO();
        resp.setNombre("Emma");
        when(mapper.mapToEntity(perReq))
                .thenReturn(new Personaje());
        when(service.altaPersonaje(any()))
                .thenReturn(new Personaje());
        when(mapper.mapToDto(any()))
                .thenReturn(resp);

        mockMvc.perform(post(this.url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objMapper.writeValueAsString(perReq))
        )

        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.nombre").value("Emma"));
    }

    @Test
    void modiPerso() throws Exception {
        PersonajeReqDTO perReq=new PersonajeReqDTO();
        PersonajeRespDTO resp=new PersonajeRespDTO();
        resp.setNombre("Emma");
        when(mapper.mapToEntity(perReq))
                .thenReturn(new Personaje());
        when(service.modiPersonaje(any(),anyInt()))
                .thenReturn(new Personaje());
        when(mapper.mapToDto(any()))
                .thenReturn(resp);

        mockMvc.perform(put(this.url+"/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objMapper.writeValueAsString(perReq))
        )

        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.nombre").value("Emma"));
    }
}