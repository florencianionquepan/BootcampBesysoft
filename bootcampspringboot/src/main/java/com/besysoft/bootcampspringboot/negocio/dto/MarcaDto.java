package com.besysoft.bootcampspringboot.negocio.dto;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Socio;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class MarcaDto {
    private Long id;
    private String nombres;

    public MarcaDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

}
