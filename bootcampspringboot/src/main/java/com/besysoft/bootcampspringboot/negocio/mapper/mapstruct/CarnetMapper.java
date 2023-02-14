package com.besysoft.bootcampspringboot.negocio.mapper.mapstruct;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Carnet;
import com.besysoft.bootcampspringboot.negocio.dto.CarnetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CarnetMapper {

    /*@Mappings({
            @Mapping(source = "codigo", target = "id"),
            @Mapping(source = "origenA", target = "destinoA"),
            @Mapping(source = "dtoB", target = "entidadB")
    })*/
    @Mapping(source = "codigo", target = "id")
    Carnet mapToEntidad(CarnetDto dto);
    @Mapping(source = "id", target = "codigo")
    CarnetDto mapToDto(Carnet carnet);

}
