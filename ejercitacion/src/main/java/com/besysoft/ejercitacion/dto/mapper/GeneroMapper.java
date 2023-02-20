package com.besysoft.ejercitacion.dto.mapper;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dto.GeneroReqDTO;
import com.besysoft.ejercitacion.dto.GeneroRespDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GeneroMapper implements IGeneroMapper{
    private final IPeliculaMapper peliMap;

    public GeneroMapper(IPeliculaMapper peliMap) {
        this.peliMap = peliMap;
    }

    @Override
    public Genero mapToEntity(GeneroReqDTO dto) {
        Genero genero=new Genero();
        genero.setId(dto.getId());
        genero.setNombre(dto.getNombre());
        genero.setListaPelis(this.peliMap.mapListToEntity(dto.getPeliculas()));
        return genero;
    }

    @Override
    public GeneroRespDTO mapToDto(Genero entity) {
        GeneroRespDTO dto=new GeneroRespDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setPeliculas(this.peliMap.mapListToDto(entity.getListaPelis()));
        return dto;
    }

    @Override
    public List<GeneroRespDTO> mapListToDto(List<Genero> generos) {
        return generos.stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }
}
