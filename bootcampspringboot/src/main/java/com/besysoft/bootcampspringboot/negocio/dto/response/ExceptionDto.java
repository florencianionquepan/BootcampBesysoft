package com.besysoft.bootcampspringboot.negocio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ExceptionDto {

    private int estado;
    private String mensaje;
    private Map<String, String> detalle;
}
