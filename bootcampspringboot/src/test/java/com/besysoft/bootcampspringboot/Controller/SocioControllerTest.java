package com.besysoft.bootcampspringboot.Controller;

import com.besysoft.bootcampspringboot.datos.DatosDummy;
import com.besysoft.bootcampspringboot.negocio.dto.SocioDto;
import com.besysoft.bootcampspringboot.negocio.mapper.SocioMapper;
import com.besysoft.bootcampspringboot.servicios.interfaz.SocioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SocioController.class)
class SocioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SocioService service;

    private ObjectMapper objectMapper;
    private String url;
    @BeforeEach
    void setUp() {
        this.url = "/socios";
        this.objectMapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void altaSocio() throws Exception{
        //GIVEN
        SocioDto dto = new SocioDto();
        dto.setNombre("NuevoSocio");
        when(service.altaSocio(SocioMapper.mapToEntity(dto)))
                .thenReturn(SocioMapper.mapToEntity(dto));

        //Transformo el objeto a JSON
        System.out.println(this.objectMapper.writeValueAsString(dto));

        //WHEN
        mockMvc.perform(
                        post(this.url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(dto))
                )
                //THEN
                .andExpect(status().isCreated());

    }

    @Test
    void verTodos() throws Exception{
        //GIVEN
        when(service.buscarTodos())
                .thenReturn(DatosDummy.getSocios());

        //WHEN
        mockMvc.perform(
                        get(this.url)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                //THEN
                .andExpect(status().isOk()) //Con status acceso al status code del response
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //Con content() al Content-Type del response
                .andExpect(jsonPath("$[0].id").value(1L)) //Con jsonPath puedo recorrer el JSON del body, el simbolo $ hace referencia a la raiz del JSON
                .andExpect(jsonPath("$[0].nombre").value("Socio Uno"));
    }
}