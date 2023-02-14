package com.besysoft.bootcampspringboot.servicios.interfaz;

import com.besysoft.bootcampspringboot.entidades.manyToOne.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


public interface MarcaService {

    Marca altaMarca(Marca marca);

    Page<Marca> paginado(Pageable pageable);

}
