package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dto.GeneroReqDTO;
import com.besysoft.ejercitacion.dto.GeneroRespDTO;
import com.besysoft.ejercitacion.dto.mapper.IGeneroMapper;
import com.besysoft.ejercitacion.servicios.interfaces.IGeneroService;
import com.besysoft.ejercitacion.utilidades.TestDatos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControladoraGenero.class)
class ControladoraGeneroTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IGeneroMapper mapper;
    @MockBean
    private IGeneroService service;
    private String url;
    private ObjectMapper objMapper;

    @BeforeEach
    void setUp() {
        this.url="/generos";
        this.objMapper=new ObjectMapper();
    }

    @Test
    void verGenerosTest() throws Exception {
        //GIVEN
        when(service.verGeneros())
                .thenReturn(TestDatos.getListaGeneros());
        //WHEN
        mockMvc.perform(get(this.url)
                        .contentType(MediaType.APPLICATION_JSON))
        //THEN
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data").value(mapper.mapListToDto(TestDatos.getListaGeneros())));
    }

    @Test
    void altaGenero() throws Exception {
        GeneroReqDTO genReq=new GeneroReqDTO();
        GeneroRespDTO resp=new GeneroRespDTO();
        resp.setNombre("Comedia");
        when(mapper.mapToEntity(genReq))
                .thenReturn(new Genero());
        when(service.altaGenero(any()))
                .thenReturn(new Genero());
        when(mapper.mapToDto(any()))
                .thenReturn(resp);

        mockMvc.perform(post(this.url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objMapper.writeValueAsString(genReq))
        )

        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.nombre").value("Comedia"));

    }

    @Test
    void modiGenero() throws Exception {
        GeneroReqDTO genReq=new GeneroReqDTO();
        GeneroRespDTO resp=new GeneroRespDTO();
        resp.setNombre("Comedia");
        when(mapper.mapToEntity(genReq)).thenReturn(new Genero());
        when(service.modiGenero(any(),anyInt())).thenReturn(new Genero());
        when(mapper.mapToDto(any())).thenReturn(resp);

        mockMvc.perform(put(this.url+"/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objMapper.writeValueAsString(genReq))
        )

        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.nombre").value("Comedia"));

    }

    @Test
    void altaGeneroError() throws Exception {
        GeneroReqDTO genReq=new GeneroReqDTO();
        GeneroRespDTO resp=new GeneroRespDTO();
        resp.setNombre("Comedia");
        when(mapper.mapToEntity(genReq)).thenReturn(new Genero());
        when(service.altaGenero(any())).thenReturn(null);

        mockMvc.perform(post(this.url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objMapper.writeValueAsString(genReq))
        )

        .andExpect(status().isBadRequest());
    }
}