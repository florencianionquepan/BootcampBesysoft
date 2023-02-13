package com.besysoft.ejercitacion.dto.mapper;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dto.GeneroReqDTO;
import com.besysoft.ejercitacion.dto.GeneroRespDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GeneroMapper implements IGeneroMapper{

    public Genero mapToEntity(GeneroReqDTO dto){
        Genero genero=new Genero();
        genero.setId(dto.getId());
        genero.setNombre(dto.getNombre());
        genero.setListaPelis(dto.getListaPelis());
        return genero;
    }

    public GeneroRespDTO mapToDto(Genero entity){
        GeneroRespDTO dto=new GeneroRespDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setListaPelis(entity.getListaPelis());
        return dto;
    }

    public List<GeneroRespDTO> mapListToDto(List<Genero> generos){
        return generos.stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }
}
