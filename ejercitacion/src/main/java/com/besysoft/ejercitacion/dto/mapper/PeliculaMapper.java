package com.besysoft.ejercitacion.dto.mapper;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dto.PeliculaReqDTO;
import com.besysoft.ejercitacion.dto.PeliculaRespDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PeliculaMapper {

    public Pelicula mapToEntity(PeliculaReqDTO dto){
        Pelicula peli=new Pelicula();
        peli.setId(dto.getId());
        peli.setTitulo(dto.getTitulo());
        peli.setFechaCreacion(dto.getFechaCreacion());
        peli.setCalificacion(dto.getCalificacion());
        peli.setListaPersonajes(dto.getPersonajes());
        return peli;
    }

    private Pelicula mapRespToEntity(PeliculaRespDTO dto){
        Pelicula peli=new Pelicula();
        peli.setId(dto.getId());
        peli.setTitulo(dto.getTitulo());
        peli.setFechaCreacion(dto.getFechaCreacion());
        peli.setCalificacion(dto.getCalificacion());
        peli.setListaPersonajes(dto.getPersonajes());
        return peli;
    }

    public PeliculaRespDTO mapToDto(Pelicula entity){
        PeliculaRespDTO dto=new PeliculaRespDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setCalificacion(entity.getCalificacion());
        dto.setPersonajes(entity.getListaPersonajes());
        return dto;
    }

    public List<PeliculaRespDTO> mapListToDto(List<Pelicula> peliculas){
        return peliculas.stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }

    //Este metodo se usa en genero. Se debe mapear el peliculaResponseDTO a Entity.
    // Ya que cuando agrego/actualizo genero uso peliculas existentes
    public List<Pelicula> mapListToEntity(List<PeliculaRespDTO> pelisDto){
        return pelisDto.stream()
                .map(this::mapRespToEntity).collect(Collectors.toList());
    }


}
