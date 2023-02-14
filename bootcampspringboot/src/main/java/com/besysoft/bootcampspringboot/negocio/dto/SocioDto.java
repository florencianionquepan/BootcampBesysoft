package com.besysoft.bootcampspringboot.negocio.dto;

import javax.validation.constraints.*;

public class SocioDto {
    private Long id;
    @NotNull
    @NotEmpty
    @Pattern(regexp="^[A-Za-z]+$", message = "Solo permite caracteres de la A a la Z")
    //@Email
    @Size(min=3, max=20)
    //@NotBlank
    private String nombre;

    public SocioDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
