package com.besysoft.ejercitacion.dto.mapper;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dto.PeliculaReqDTO;
import com.besysoft.ejercitacion.dto.PeliculaRespDTO;

import java.util.List;

public interface IPeliculaMapper {
    Pelicula mapToEntity(PeliculaReqDTO dto);
    PeliculaRespDTO mapToDto(Pelicula entity);
    List<PeliculaRespDTO> mapListToDto(List<Pelicula> peliculas);
    List<Pelicula> mapListToEntity(List<PeliculaRespDTO> pelisDto);
}
