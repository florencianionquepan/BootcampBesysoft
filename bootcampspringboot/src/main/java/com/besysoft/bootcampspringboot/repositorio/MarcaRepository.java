package com.besysoft.bootcampspringboot.repositorio;

import com.besysoft.bootcampspringboot.entidades.manyToOne.Marca;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MarcaRepository extends PagingAndSortingRepository<Marca, Long>{
}
