package com.besysoft.ejercitacion.dto.mapper;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dto.GeneroReqDTO;
import com.besysoft.ejercitacion.dto.GeneroRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IGeneroMapper {
    @Mapping(source="peliculas", target="listaPelis")
    Genero mapToEntity(GeneroReqDTO dto);

    @Mapping(source="listaPelis", target="peliculas")
    GeneroRespDTO mapToDto(Genero entity);

    List<GeneroRespDTO> mapListToDto(List<Genero> generos);
}
