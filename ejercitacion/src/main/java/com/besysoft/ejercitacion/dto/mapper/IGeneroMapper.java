package com.besysoft.ejercitacion.dto.mapper;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dto.GeneroReqDTO;
import com.besysoft.ejercitacion.dto.GeneroRespDTO;

import java.util.List;

public interface IGeneroMapper {
    Genero mapToEntity(GeneroReqDTO dto);
    GeneroRespDTO mapToDto(Genero entity);
    List<GeneroRespDTO> mapListToDto(List<Genero> generos);
}
