package com.besysoft.bootcampspringboot.repositorio;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Socio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

public interface SocioRepository extends CrudRepository<Socio, Long> {

    @Query("from Socio s where s.nombre= ?1")
    Optional<Socio> buscarPorNombre(String nombre);

}
