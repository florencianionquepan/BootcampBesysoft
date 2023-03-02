package com.besysoft.ejercitacion.controlador;

import com.besysoft.ejercitacion.dto.mapper.IGeneroMapper;
import com.besysoft.ejercitacion.servicios.interfaces.IGeneroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void verGenerosTest(){

    }

    @Test
    void altaGenero() {
    }

    @Test
    void modiGenero() {
    }
}