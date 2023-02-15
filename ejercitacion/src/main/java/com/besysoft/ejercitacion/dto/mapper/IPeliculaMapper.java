package com.besysoft.ejercitacion.dto.mapper;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dto.PeliculaReqDTO;
import com.besysoft.ejercitacion.dto.PeliculaRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface IPeliculaMapper {

    @Mapping(source="personajes", target="listaPersonajes")
    Pelicula mapToEntity(PeliculaReqDTO dto);

    @Mapping(source = "personajes", target = "listaPersonajes")
    Pelicula mapRespToEntity(PeliculaRespDTO dtoResp);

    @Mapping(source="listaPersonajes", target="personajes")
    PeliculaRespDTO mapToDto(Pelicula entity);
    List<PeliculaRespDTO> mapListToDto(List<Pelicula> peliculas);
    List<Pelicula> mapListToEntity(List<PeliculaRespDTO> pelisDto);
}
