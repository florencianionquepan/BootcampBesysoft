package com.besysoft.ejercitacion.dto.mapper;

import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.dto.PersonajeReqDTO;
import com.besysoft.ejercitacion.dto.PersonajeRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPersonajeMapper {
    @Mapping(source = "peliculas", target="listaPeliculas")
    Personaje mapToEntity(PersonajeReqDTO dto);

    @Mapping(source = "peliculas", target="listaPeliculas")
    Personaje mapRespToEntity(PersonajeRespDTO dtoResp);

    @Mapping(source = "listaPeliculas", target="peliculas")
    PersonajeRespDTO mapToDto(Personaje entity);

    List<PersonajeRespDTO> mapListToDto(List<Personaje> personajes);

    List<Personaje> mapListToEntity(List<PersonajeRespDTO> persosDto);
}
