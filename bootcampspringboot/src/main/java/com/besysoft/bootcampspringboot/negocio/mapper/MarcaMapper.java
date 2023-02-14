package com.besysoft.bootcampspringboot.negocio.mapper;

import com.besysoft.bootcampspringboot.entidades.manyToOne.Marca;
import com.besysoft.bootcampspringboot.negocio.dto.MarcaDto;

public class MarcaMapper {
    public static MarcaDto mapToDto(Marca entidad){
        MarcaDto dto = new MarcaDto();
        dto.setId(entidad.getId());
        dto.setNombres(entidad.getNombre());
        return dto;
    }
}
