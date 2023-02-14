package com.besysoft.bootcampspringboot.negocio.mapper;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Socio;
import com.besysoft.bootcampspringboot.negocio.dto.SocioDto;

import java.util.List;
import java.util.stream.Collectors;

public class SocioMapper {

    public static Socio mapToEntity(SocioDto dto){
        Socio socio= new Socio();
        socio.setNombre(dto.getNombre());
        socio.setId(dto.getId());
        return socio;
    }

    public static SocioDto mapToDto(Socio entity){
        SocioDto dto=new SocioDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }

    public static List<SocioDto> mapListToDto(List<Socio> socios){
        return socios.stream().map(SocioMapper::mapToDto).collect(Collectors.toList());
    }
}
