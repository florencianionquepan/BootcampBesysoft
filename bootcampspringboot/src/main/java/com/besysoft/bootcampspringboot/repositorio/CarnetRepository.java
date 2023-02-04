package com.besysoft.bootcampspringboot.repositorio;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Carnet;
import org.springframework.data.repository.CrudRepository;

public interface CarnetRepository extends CrudRepository<Carnet,Long> {
}
